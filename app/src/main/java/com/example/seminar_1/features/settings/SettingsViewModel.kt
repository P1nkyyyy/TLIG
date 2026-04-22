package com.example.seminar_1.features.settings

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seminar_1.core.notifications.NotificationHelper
import com.example.seminar_1.features.settings.data.local.SettingsPreferences
import com.example.seminar_1.features.settings.data.model.ThemeMode
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val settingsPreferences: SettingsPreferences
) : ViewModel() {

    /*#region Notifications*/
    var isDailyNotificationEnabled by mutableStateOf(settingsPreferences.isDailyNotificationEnabled)
        private set

    var selectedHour by mutableIntStateOf(settingsPreferences.notificationHour)
        private set

    var selectedMinute by mutableIntStateOf(settingsPreferences.notificationMinute)
        private set

    fun toggleNotifications(enabled: Boolean) {
        isDailyNotificationEnabled = enabled
        settingsPreferences.isDailyNotificationEnabled = enabled
        updateNotificationSchedule()
    }

    fun updateTime(hour: Int, minute: Int) {
        selectedHour = hour
        selectedMinute = minute
        settingsPreferences.notificationHour = hour
        settingsPreferences.notificationMinute = minute
        updateNotificationSchedule()
    }

    private fun updateNotificationSchedule() {
        if (isDailyNotificationEnabled) {
            NotificationHelper.scheduleDailyNotification(context, selectedHour, selectedMinute)
        } else {
            NotificationHelper.cancelDailyNotification(context)
        }
    }
    /*#endregion Notifications*/

    /*#region Theme*/
    val themeMode = settingsPreferences.themeModeFlow
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000), settingsPreferences.themeMode
        )

    fun setTheme(mode: ThemeMode) {
        settingsPreferences.themeMode = mode
    }
    /*#endregion Theme*/
}
