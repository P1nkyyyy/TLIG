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
    var isDailyMessageNotificationEnabled by mutableStateOf(settingsPreferences.isDailyMessageNotificationEnabled)
        private set
    var dailyMessageNotificationHour by mutableIntStateOf(settingsPreferences.dailyMessageNotificationHour)
        private set
    var dailyMessageNotificationMinute by mutableIntStateOf(settingsPreferences.dailyMessageNotificationMinute)
        private set

    var isDailyPrayersNotificationEnabled by mutableStateOf(settingsPreferences.isDailyPrayersNotificationEnabled)
        private set
    var dailyPrayersNotificationHour by mutableIntStateOf(settingsPreferences.dailyPrayersNotificationHour)
        private set
    var dailyPrayersNotificationMinute by mutableIntStateOf(settingsPreferences.dailyPrayersNotificationMinute)
        private set

    fun toggleDailyMessageTime(enabled: Boolean) {
        isDailyMessageNotificationEnabled = enabled
        settingsPreferences.isDailyMessageNotificationEnabled = enabled
        updateDailyMessageSchedule()
    }

    fun updateDailyMessageTime(hour: Int, minute: Int) {
        dailyMessageNotificationHour = hour
        dailyMessageNotificationMinute = minute
        settingsPreferences.dailyMessageNotificationHour = hour
        settingsPreferences.dailyMessageNotificationMinute = minute
        updateDailyMessageSchedule()
    }

    private fun updateDailyMessageSchedule() {
        if (isDailyMessageNotificationEnabled) {
            NotificationHelper.scheduleDailyNotification(
                context,
                dailyMessageNotificationHour,
                dailyMessageNotificationMinute
            )
        } else {
            NotificationHelper.cancelDailyMessagesNotification(context)
        }
    }

    fun toggleDailyPrayersTime(enabled: Boolean) {
        isDailyPrayersNotificationEnabled = enabled
        settingsPreferences.isDailyPrayersNotificationEnabled = enabled
        updateDailyPrayersSchedule()
    }

    fun updateDailyPrayersTime(hour: Int, minute: Int) {
        dailyPrayersNotificationHour = hour
        dailyPrayersNotificationMinute = minute
        settingsPreferences.dailyPrayersNotificationHour = hour
        settingsPreferences.dailyPrayersNotificationMinute = minute
        updateDailyPrayersSchedule()
    }

    private fun updateDailyPrayersSchedule() {
        if (isDailyPrayersNotificationEnabled) {
            NotificationHelper.scheduleThreeDailyPrayers(
                context,
                dailyPrayersNotificationHour,
                dailyPrayersNotificationMinute
            )
        } else {
            NotificationHelper.cancelThreeDailyPrayers(context)
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
