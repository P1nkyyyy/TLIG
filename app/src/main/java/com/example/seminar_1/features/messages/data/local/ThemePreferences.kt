package com.example.seminar_1.features.messages.data.local

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemePreferences @Inject constructor(@ApplicationContext context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences("theme_settings", Context.MODE_PRIVATE)

    var textSize: Int
        get() = sharedPreferences.getInt(KEY_TEXT_SIZE, 18)
        set(value) = sharedPreferences.edit { putInt(KEY_TEXT_SIZE, value) }

    var lineHeight: Float
        get() = sharedPreferences.getFloat(KEY_LINE_HEIGHT, 1.5f)
        set(value) = sharedPreferences.edit { putFloat(KEY_LINE_HEIGHT, value) }

    var selectedFont: String
        get() = sharedPreferences.getString(KEY_SELECTED_FONT, "Serif") ?: "Serif"
        set(value) = sharedPreferences.edit { putString(KEY_SELECTED_FONT, value) }

    var backgroundColor: Color
        get() = Color(sharedPreferences.getLong(KEY_BACKGROUND_COLOR, 0xFF1B2536L))
        set(value) = sharedPreferences.edit {
            putLong(
                KEY_BACKGROUND_COLOR,
                value.toArgb().toLong()
            )
        }

    var contentColor: Color
        get() = Color(sharedPreferences.getLong(KEY_CONTENT_COLOR, 0xFFFFFFFFL))
        set(value) = sharedPreferences.edit { putLong(KEY_CONTENT_COLOR, value.toArgb().toLong()) }

    fun resetToDefault() {
        sharedPreferences.edit { clear() }
    }
    
    companion object {
        private const val KEY_TEXT_SIZE = "text_size"
        private const val KEY_LINE_HEIGHT = "line_height"
        private const val KEY_SELECTED_FONT = "selected_font"
        private const val KEY_BACKGROUND_COLOR = "background_color"
        private const val KEY_CONTENT_COLOR = "content_color"
    }
}
