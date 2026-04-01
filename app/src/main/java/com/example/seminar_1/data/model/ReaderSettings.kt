package com.example.seminar_1.data.model

import androidx.compose.ui.graphics.Color

data class ReaderSettings(
    val fontSize: Int = 18,
    val lineHeight: Float = 1.5f,
    val fontFamily: String = "Serif",
    val backgroundColor: Color = Color(0xFF1B2536),
    val contentColor: Color = Color(0xFFE3EAF3)
)