package com.example.seminar_1.features.messages.data.model

import androidx.compose.ui.graphics.Color

data class MessageThemeSettingsActions(
    val onTextSizeChange: (Int) -> Unit,
    val onFontChange: (String) -> Unit,
    val onThemeColorsChange: (Color, Color) -> Unit,
    val onLineHeightChange: (Float) -> Unit,
)
