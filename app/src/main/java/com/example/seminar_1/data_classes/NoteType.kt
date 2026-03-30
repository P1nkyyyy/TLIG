package com.example.seminar_1.data_classes

import androidx.room.Entity

@Entity(tableName = "notes", primaryKeys = ["id", "messageId"])
data class NoteType(
    val id: Int,
    val messageId: Int,
    val content: String
)