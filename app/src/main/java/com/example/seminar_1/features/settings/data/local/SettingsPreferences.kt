package com.example.seminar_1.features.settings.data.local

import android.content.Context
import androidx.core.content.edit
import com.example.seminar_1.features.settings.data.model.ThemeMode
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsPreferences @Inject constructor(@ApplicationContext context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences("settings_prefs", Context.MODE_PRIVATE)

    var isDailyNotificationEnabled: Boolean
        get() = sharedPreferences.getBoolean(KEY_NOTIFICATIONS_ENABLED, false)
        set(value) = sharedPreferences.edit { putBoolean(KEY_NOTIFICATIONS_ENABLED, value) }

    var notificationHour: Int
        get() = sharedPreferences.getInt(KEY_NOTIFICATION_HOUR, 8)
        set(value) = sharedPreferences.edit { putInt(KEY_NOTIFICATION_HOUR, value) }

    var notificationMinute: Int
        get() = sharedPreferences.getInt(KEY_NOTIFICATION_MINUTE, 0)
        set(value) = sharedPreferences.edit { putInt(KEY_NOTIFICATION_MINUTE, value) }

    private val _themeMode = MutableStateFlow(themeMode)
    val themeModeFlow = _themeMode.asStateFlow()
    var themeMode: ThemeMode
        get() = ThemeMode.valueOf(
            sharedPreferences.getString(KEY_THEME_MODE, ThemeMode.SYSTEM.name)
                ?: ThemeMode.SYSTEM.name
        )
        set(value) {
            sharedPreferences.edit { putString(KEY_THEME_MODE, value.name) }
            _themeMode.value = value
        }

    companion object {
        private const val KEY_NOTIFICATIONS_ENABLED = "notifications_enabled"
        private const val KEY_NOTIFICATION_HOUR = "notification_hour"
        private const val KEY_NOTIFICATION_MINUTE = "notification_minute"
        private const val KEY_THEME_MODE = "theme_mode"
    }
}