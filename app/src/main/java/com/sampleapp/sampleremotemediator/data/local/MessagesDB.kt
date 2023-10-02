package com.sampleapp.sampleremotemediator.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sampleapp.sampleremotemediator.data.local.entities.MessageDb

@Database(
    entities = [MessageDb::class],
    version = 1
)
abstract class MessagesDB : RoomDatabase() {
    abstract val dao : MessagesDAO
}