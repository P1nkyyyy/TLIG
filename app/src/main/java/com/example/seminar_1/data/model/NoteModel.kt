package com.example.seminar_1.data.model

import androidx.room.Entity

@Entity(tableName = "notes", primaryKeys = ["id", "messageId"])
data class NoteModel(
    val id: Int,
    val messageId: Int,
    val content: String
)