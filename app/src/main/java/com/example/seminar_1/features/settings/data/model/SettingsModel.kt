package com.example.seminar_1.features.settings.data.model

data class SettingsModel(
    val isDailyNotificationEnabled: Boolean,
    val selectedHour: Int,
    val selectedMinute: Int,
    val toggleNotifications: (Boolean) -> Unit,
    val updateTime: (Int, Int) -> Unit,
    val themeMode: ThemeMode,
)
