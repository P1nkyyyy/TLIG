package com.example.seminar_1.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.seminar_1.features.messages.data.model.MessageModel
import com.example.seminar_1.features.messages.data.model.NoteModel

@Database(entities = [MessageModel::class, NoteModel::class], version = 1)
abstract class TligDatabase : RoomDatabase() {
    abstract fun messagesDao(): MessagesDao
    abstract fun notesDao(): NotesDao

    companion object {
        @Volatile
        private var INSTANCE: TligDatabase? = null

        fun getDatabase(context: Context): TligDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TligDatabase::class.java,
                    "messages_database"
                )
                    .createFromAsset("database/poselstvi.db")
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}