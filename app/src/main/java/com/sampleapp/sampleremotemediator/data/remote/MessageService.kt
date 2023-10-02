package com.sampleapp.sampleremotemediator.data.remote

import com.sampleapp.sampleremotemediator.data.remote.dto.MessageDto

interface MessageService {


    suspend fun refreshChatMessages() : List<MessageDto>

    suspend fun getNewChatMessages(lastMessage : Int) : List<MessageDto>


}