package com.sampleapp.sampleremotemediator.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.sampleapp.sampleremotemediator.data.local.MessagesDB
import com.sampleapp.sampleremotemediator.data.local.entities.MessageDb
import com.sampleapp.sampleremotemediator.data.mappers.toMessageDb
import com.sampleapp.sampleremotemediator.data.remote.MessageService
import kotlinx.coroutines.delay

const val TAG = "CHECKTAGS"

@OptIn(ExperimentalPagingApi::class)
class MessagesRemoteMediator(
    private val db : MessagesDB,
    private val messageService : MessageService
) : RemoteMediator<Int, MessageDb>() {


    override suspend fun initialize(): InitializeAction {
        // If there are already messages in db skip refresh
        return if(db.dao.checkIfElementsExist())
            InitializeAction.SKIP_INITIAL_REFRESH
        else InitializeAction.LAUNCH_INITIAL_REFRESH

    }


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MessageDb>
    ): MediatorResult {

        return try {

            val response = when (loadType) {
                LoadType.REFRESH -> {
                    messageService.refreshChatMessages()

                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    Log.d(TAG, "append is called only after items added to db on initial refresh")
                    val lastItem = state.lastItemOrNull()?.message
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
//                    append not working on initially empty db without:
//                    1) delay
//                    2) getting last item from db
//                    3) always passing false to endOfPaginationReached in this block

                    messageService.getNewChatMessages(lastItem)

                }
            }

            db.withTransaction {

                db.dao.insertNewMessages(response.map {dto ->
                    dto.toMessageDb()
                })
            }

            if(loadType == LoadType.REFRESH){
                    Log.d(TAG, "loaded on refresh : ${response.size}")
                    Log.d(TAG, "are items added to DB : ${db.dao.checkIfElementsExist()}")
//                    delay(3000)
            }


            MediatorResult.Success(endOfPaginationReached = response.size < state.config.pageSize)

        } catch (e: Exception){
            MediatorResult.Error(e)
        }

    }
}