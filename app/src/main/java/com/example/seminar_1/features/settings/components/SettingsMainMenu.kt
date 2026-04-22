package com.example.seminar_1.features.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seminar_1.R
import com.example.seminar_1.features.settings.components.notifications.NotificationTimeSelection
import com.example.seminar_1.features.settings.components.notifications.NotificationToggleItem
import com.example.seminar_1.features.settings.data.model.SettingsModel
import com.example.seminar_1.features.settings.utils.getThemeString

@Composable
fun SettingsMainMenu(
    settings: SettingsModel,
    onOpenTheme: () -> Unit,
    onOpenLanguage: () -> Unit
) {
    Column {
        Text(
            text = "Nastavení",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

        SettingsSection(title = stringResource(R.string.settings_main_menu_notifications)) {
            NotificationToggleItem(
                isChecked = settings.isDailyNotificationEnabled,
                onCheckedChange = { settings.toggleNotifications(it) }
            )
            NotificationTimeSelection(settings)
        }

        SettingsSection(title = stringResource(R.string.settings_main_menu_customization)) {
            SettingsItem(
                icon = Icons.Rounded.Palette,
                title = stringResource(R.string.settings_theme_menu_title),
                value = getThemeString(settings.themeMode),
                onClick = onOpenTheme
            )
            SettingsItem(
                icon = Icons.Rounded.Language,
                title = stringResource(R.string.settings_language_menu_title),
                value = "Čeština",
                onClick = onOpenLanguage
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Verze 1.0.0",
            color = Color.White.copy(alpha = 0.3f),
            fontSize = 12.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}