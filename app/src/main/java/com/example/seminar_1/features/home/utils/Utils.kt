package com.example.seminar_1.features.home.utils

import com.example.seminar_1.features.messages.data.model.MessageModel

/** Vypočítá progress uživatele kde se nachází v čtení. */
fun calculateProgressOfReading(
    message: MessageModel
): Float {
    val paragraphs = message.content.split("\n\n").filter { it.isNotBlank() }
    val progress = if (paragraphs.isNotEmpty()) {
        (message.lastReadParagraph.toFloat() / (paragraphs.size - 1).coerceAtLeast(1)).coerceIn(
            0f,
            1f
        )
    } else 0f
    return progress
}