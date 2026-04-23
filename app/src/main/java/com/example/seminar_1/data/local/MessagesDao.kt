package com.example.seminar_1.data.local

import androidx.room.Dao
import androidx.room.Query
import com.example.seminar_1.features.messages.data.model.MessageModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MessagesDao {
    @Query("SELECT * FROM messages")
    fun getAll(): Flow<List<MessageModel>>

    @Query("SELECT * FROM messages WHERE id = :messageId")
    fun get(messageId: Int): Flow<MessageModel>

    @Query("UPDATE messages SET isArchived = :isArchived, archivedAt = :archivedAt WHERE id = :messageId")
    suspend fun updateArchive(messageId: Int, isArchived: Boolean, archivedAt: Long?)

    @Query("UPDATE messages SET lastReadParagraph = :paragraphIndex WHERE id = :messageId")
    suspend fun updateLastReadParagraph(messageId: Int, paragraphIndex: Int)

    @Query("UPDATE messages SET lastOpenedMessage = :timestamp WHERE id = :messageId")
    suspend fun updateLastOpenedMessage(messageId: Int, timestamp: Long)

    @Query("SELECT * FROM messages WHERE lastOpenedMessage > 0 ORDER BY lastOpenedMessage DESC LIMIT 1")
    fun getLastOpenedMessage(): Flow<MessageModel?>

    @Query("SELECT * FROM messages ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomMessage(): MessageModel?

    @Query("SELECT COUNT(*) FROM messages")
    suspend fun getMessagesCount(): Int
}
