package com.example.seminar_1

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.seminar_1.data_classes.Message

@Database(entities = [Message::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messagesDao(): MessagesDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
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
