package com.example.seminar_1.core.di

import android.content.Context
import androidx.room.Room
import com.example.seminar_1.data.local.MessagesDao
import com.example.seminar_1.data.local.NotesDao
import com.example.seminar_1.data.local.TligDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TligDatabase {
        return Room.databaseBuilder(
            context,
            TligDatabase::class.java,
            "messages_database"
        )
            .createFromAsset("database/poselstvi.db")
            .build()
    }

    @Provides
    fun provideMessagesDao(database: TligDatabase): MessagesDao {
        return database.messagesDao()
    }

    @Provides
    fun provideNotesDao(database: TligDatabase): NotesDao {
        return database.notesDao()
    }
}