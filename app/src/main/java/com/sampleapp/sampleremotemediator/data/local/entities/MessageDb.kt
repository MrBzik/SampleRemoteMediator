package com.sampleapp.sampleremotemediator.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MessageDb(
    @PrimaryKey
    val message : Int
)
