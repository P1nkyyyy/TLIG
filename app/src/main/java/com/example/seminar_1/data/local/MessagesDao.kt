package com.example.seminar_1.data.local

import androidx.room.Dao
import androidx.room.Query
import com.example.seminar_1.data.model.MessageModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MessagesDao {
    @Query("SELECT * FROM messages")
    fun getAll(): Flow<List<MessageModel>>

    @Query("SELECT * FROM messages WHERE id = :messageId")
    fun get(messageId: Int): Flow<MessageModel>

    @Query("UPDATE messages SET isArchived = :isArchived WHERE id = :messageId")
    suspend fun updateArchive(messageId: Int, isArchived: Boolean)
}
