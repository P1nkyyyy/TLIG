package com.example.seminar_1

import android.app.Application
import com.example.seminar_1.data.local.TligDatabase
import com.example.seminar_1.data.repository.MessageRepository

class TligApplication : Application() {
    val database by lazy { TligDatabase.getDatabase(this) }

    val messageRepository by lazy { MessageRepository(database.messagesDao(), database.notesDao()) }
}