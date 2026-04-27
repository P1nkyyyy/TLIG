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

    var isDailyMessageNotificationEnabled: Boolean
        get() = sharedPreferences.getBoolean(KEY_MESSAGE_NOTIFICATION_ENABLED, false)
        set(value) = sharedPreferences.edit { putBoolean(KEY_MESSAGE_NOTIFICATION_ENABLED, value) }

    var dailyMessageNotificationHour: Int
        get() = sharedPreferences.getInt(KEY_MESSAGE_NOTIFICATION_HOUR, 8)
        set(value) = sharedPreferences.edit { putInt(KEY_MESSAGE_NOTIFICATION_HOUR, value) }

    var dailyMessageNotificationMinute: Int
        get() = sharedPreferences.getInt(KEY_MESSAGE_NOTIFICATION_MINUTE, 0)
        set(value) = sharedPreferences.edit { putInt(KEY_MESSAGE_NOTIFICATION_MINUTE, value) }

    var isDailyPrayersNotificationEnabled: Boolean
        get() = sharedPreferences.getBoolean(KEY_PRAYERS_NOTIFICATION_ENABLED, false)
        set(value) = sharedPreferences.edit { putBoolean(KEY_PRAYERS_NOTIFICATION_ENABLED, value) }
    
    var dailyPrayersNotificationHour: Int
        get() = sharedPreferences.getInt(KEY_PRAYERS_NOTIFICATION_HOUR, 9)
        set(value) = sharedPreferences.edit { putInt(KEY_PRAYERS_NOTIFICATION_HOUR, value) }

    var dailyPrayersNotificationMinute: Int
        get() = sharedPreferences.getInt(KEY_PRAYERS_NOTIFICATION_MINUTE, 0)
        set(value) = sharedPreferences.edit { putInt(KEY_PRAYERS_NOTIFICATION_MINUTE, value) }

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
        private const val KEY_MESSAGE_NOTIFICATION_ENABLED = "message_notification_enabled"
        private const val KEY_MESSAGE_NOTIFICATION_HOUR = "message_notification_hour"
        private const val KEY_MESSAGE_NOTIFICATION_MINUTE = "message_notification_minute"

        private const val KEY_PRAYERS_NOTIFICATION_ENABLED = "prayers_notification_enabled"
        private const val KEY_PRAYERS_NOTIFICATION_HOUR = "prayers_notification_hour"
        private const val KEY_PRAYERS_NOTIFICATION_MINUTE = "prayers_notification_minute"

        private const val KEY_THEME_MODE = "theme_mode"
    }
}