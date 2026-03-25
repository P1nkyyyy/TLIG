package com.example.seminar_1.data_classes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageType(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val date: String,
    val content: String,
    val isArchived: Boolean,
    val isCompleted: Boolean
)