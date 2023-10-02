package com.sampleapp.sampleremotemediator.data.remote

import com.sampleapp.sampleremotemediator.data.remote.dto.MessageDto
import com.sampleapp.sampleremotemediator.utils.Constants.PAGE_SIZE
import kotlinx.coroutines.delay

class FakeMessageService : MessageService {

    private val fakeItems = MutableList(1000){index ->
        MessageDto(index)
    }


    override suspend fun refreshChatMessages(): List<MessageDto> {
        delay(1000)
        return fakeItems.subList(0, PAGE_SIZE * 3)
    }

    override suspend fun getNewChatMessages(lastMessage: Int): List<MessageDto> {
        delay(1000)
        return fakeItems.subList(lastMessage + 1, lastMessage + PAGE_SIZE + 1)
    }

}