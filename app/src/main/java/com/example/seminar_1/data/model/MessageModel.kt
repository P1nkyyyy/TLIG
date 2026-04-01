package com.example.seminar_1.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val date: String,
    val content: String,
    val isArchived: Boolean,
    val isCompleted: Boolean
)