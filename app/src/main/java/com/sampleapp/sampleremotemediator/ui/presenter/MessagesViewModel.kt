package com.sampleapp.sampleremotemediator.ui.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.sampleapp.sampleremotemediator.data.MessagesRemoteMediator
import com.sampleapp.sampleremotemediator.data.local.MessagesDB
import com.sampleapp.sampleremotemediator.data.mappers.toMessagePresent
import com.sampleapp.sampleremotemediator.data.remote.MessageService
import com.sampleapp.sampleremotemediator.utils.Constants.PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel @Inject constructor(
    private val db : MessagesDB,
    private val messageService : MessageService
) : ViewModel() {

    @OptIn(ExperimentalPagingApi::class)
    fun returnMessagesPagingSource() : Flow<PagingData<MessagePresent>>{

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE
            ),
            remoteMediator = MessagesRemoteMediator(db, messageService)
        ){
            db.dao.pagingSource()
        }.flow.map {
            it.map { messageDb ->
                messageDb.toMessagePresent()
            }
        }.cachedIn(viewModelScope)

    }


}