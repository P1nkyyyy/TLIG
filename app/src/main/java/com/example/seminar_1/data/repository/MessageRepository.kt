package com.example.seminar_1.data.repository

import com.example.seminar_1.data.local.MessagesDao
import com.example.seminar_1.data_classes.MessageType
import kotlinx.coroutines.flow.Flow

class MessageRepository(private val messagesDao: MessagesDao) {
    fun getAllMessages(): Flow<List<MessageType>> {
        return messagesDao.getAll()
    }

    fun getMessageById(id: Int): Flow<MessageType> {
        return messagesDao.get(id)
    }
}
