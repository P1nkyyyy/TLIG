package com.example.seminar_1.data.repository

import com.example.seminar_1.data.local.MessagesDao
import com.example.seminar_1.data.local.NotesDao
import com.example.seminar_1.features.messages.data.model.MessageModel
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageRepository @Inject constructor(
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

    suspend fun updateLastReadParagraph(id: Int, paragraphIndex: Int) =
        messagesDao.updateLastReadParagraph(id, paragraphIndex)

    suspend fun updateLastOpenedMessage(id: Int, timeStamp: Long) =
        messagesDao.updateLastOpenedMessage(id, timeStamp)

    fun getLastOpenedMessage(): Flow<MessageModel?> = messagesDao.getLastOpenedMessage()


    /**
     * Notes
     */
    fun getNoteById(noteId: Int, messageId: Int) = notesDao.getNote(noteId, messageId)
}
