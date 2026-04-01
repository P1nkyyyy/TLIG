package com.example.seminar_1.data.repository

import com.example.seminar_1.data.local.MessagesDao
import com.example.seminar_1.data.local.NotesDao
import com.example.seminar_1.data.model.MessageModel
import kotlinx.coroutines.flow.Flow

class MessageRepository(
    private val messagesDao: MessagesDao,
    private val notesDao: NotesDao,
) {
    /**
     * Messages
     */
    fun getAllMessages(): Flow<List<MessageModel>> = messagesDao.getAll()

    fun getMessageById(id: Int): Flow<MessageModel> = messagesDao.get(id)

    suspend fun updateArchive(id: Int, isArchived: Boolean): Unit =
        messagesDao.updateArchive(id, isArchived)

    /**
     * Notes
     */
    fun getNoteById(id: Int, messageId: Int) = notesDao.getNote(id, messageId)
}
