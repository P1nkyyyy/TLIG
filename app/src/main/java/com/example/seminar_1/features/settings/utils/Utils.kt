package com.example.seminar_1.features.settings.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.seminar_1.R
import com.example.seminar_1.features.settings.data.model.ThemeMode

@Composable
fun getThemeString(themeMode: ThemeMode): String {
    return when (themeMode) {
        ThemeMode.LIGHT -> stringResource(R.string.settings_theme_menu_light)
        ThemeMode.DARK -> stringResource(R.string.settings_theme_menu_dark)
        ThemeMode.SYSTEM -> stringResource(R.string.settings_theme_menu_system)
    }
}