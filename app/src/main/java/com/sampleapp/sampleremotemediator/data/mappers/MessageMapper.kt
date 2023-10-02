package com.sampleapp.sampleremotemediator.data.mappers

import com.sampleapp.sampleremotemediator.data.local.entities.MessageDb
import com.sampleapp.sampleremotemediator.data.remote.dto.MessageDto
import com.sampleapp.sampleremotemediator.ui.presenter.MessagePresent

fun MessageDto.toMessageDb() : MessageDb {
    return MessageDb(
        message = message
    )
}

fun MessageDb.toMessagePresent() : MessagePresent {
    return MessagePresent(
        message = message
    )
}