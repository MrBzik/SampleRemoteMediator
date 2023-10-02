package com.sampleapp.sampleremotemediator.di

import android.content.Context
import androidx.room.Room
import com.sampleapp.sampleremotemediator.data.local.MessagesDB
import com.sampleapp.sampleremotemediator.data.remote.MessageService
import com.sampleapp.sampleremotemediator.data.remote.FakeMessageService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesMessagesDB(@ApplicationContext context: Context) : MessagesDB {
        return Room.databaseBuilder(
            context,
            MessagesDB::class.java,
            "messages_db"
        )
            .build()
    }


    @Provides
    @Singleton
    fun providesMessageService() : MessageService {
        return FakeMessageService()
    }

}