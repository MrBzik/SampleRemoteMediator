package com.sampleapp.sampleremotemediator.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sampleapp.sampleremotemediator.data.local.entities.MessageDb

@Dao
interface MessagesDAO {

    @Upsert
    suspend fun insertNewMessages(messages : List<MessageDb>)

    @Query("SELECT EXISTS(SELECT * FROM MessageDb)")
    suspend fun checkIfElementsExist() : Boolean

    @Query("SELECT * FROM MessageDb ORDER BY message")
    fun pagingSource() : PagingSource<Int, MessageDb>

}