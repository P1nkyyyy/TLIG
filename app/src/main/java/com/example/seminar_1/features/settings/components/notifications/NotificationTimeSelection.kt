package com.example.seminar_1.features.settings.components.notifications

import android.app.TimePickerDialog
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.seminar_1.R
import com.example.seminar_1.features.settings.components.SettingsItem
import com.example.seminar_1.features.settings.data.model.SettingsModel
import com.example.seminar_1.ui.theme.spacing
import java.util.Locale.getDefault

@Composable
fun NotificationTimeSelection(
    settings: SettingsModel,
) {
    val context = LocalContext.current

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour: Int, minute: Int ->
            settings.updateTime(hour, minute)
        },
        settings.selectedHour,
        settings.selectedMinute,
        true
    )

    AnimatedVisibility(
        visible = settings.isDailyNotificationEnabled,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut()
    ) {
        Column {
            Box(modifier = Modifier.padding(vertical = MaterialTheme.spacing.base1)) {
                HorizontalDivider(color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            SettingsItem(
                icon = Icons.Rounded.Schedule,
                title = stringResource(R.string.settings_notifications_time_title),
                value = String.format(
                    getDefault(),
                    "%02d:%02d",
                    settings.selectedHour,
                    settings.selectedMinute
                ),
                onClick = { timePickerDialog.show() }
            )
        }
    }
}