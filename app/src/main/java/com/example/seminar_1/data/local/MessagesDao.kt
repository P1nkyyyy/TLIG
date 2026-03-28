package com.example.seminar_1.data.local

import androidx.room.Dao
import androidx.room.Query
import com.example.seminar_1.data_classes.MessageType
import kotlinx.coroutines.flow.Flow

@Dao
interface MessagesDao {
    @Query("SELECT * FROM messages")
    fun getAll(): Flow<List<MessageType>>

    @Query("SELECT * FROM messages WHERE id = :id")
    fun get(id: Int): Flow<MessageType>
}