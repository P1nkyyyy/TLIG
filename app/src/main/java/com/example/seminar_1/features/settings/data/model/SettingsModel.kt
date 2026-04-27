package com.example.seminar_1.features.settings.data.model

data class SettingsModel(
    val dailyMessage: NotificationSettings,
    val threePrayers: NotificationSettings,
    val themeMode: ThemeMode,
)
