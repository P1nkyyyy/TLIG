package com.example.seminar_1.features.settings

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.seminar_1.features.settings.components.SettingsMainMenu
import com.example.seminar_1.features.settings.components.theme.SettingsThemeContent
import com.example.seminar_1.features.settings.data.model.SettingsModel
import com.example.seminar_1.ui.theme.spacing

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    var currentScreen by remember { mutableStateOf("main") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = MaterialTheme.spacing.base3)
            .statusBarsPadding()
    ) {
        AnimatedContent(
            targetState = currentScreen,
            transitionSpec = {
                if (targetState != "main") {
                    (slideInHorizontally { width -> width }).togetherWith(
                        slideOutHorizontally { width -> -width })
                } else {
                    (slideInHorizontally { width -> -width }).togetherWith(
                        slideOutHorizontally { width -> width })
                }
            },
            label = "settings_navigation"
        ) { screen ->
            when (screen) {
                "main" -> {
                    SettingsMainMenu(
                        SettingsModel(
                            isDailyNotificationEnabled = viewModel.isDailyNotificationEnabled,
                            selectedHour = viewModel.selectedHour,
                            selectedMinute = viewModel.selectedMinute,
                            toggleNotifications = { enabled -> viewModel.toggleNotifications(enabled) },
                            updateTime = { hour, minute -> viewModel.updateTime(hour, minute) },
                            themeMode = viewModel.themeMode.collectAsState().value
                        ),
                        onOpenTheme = { currentScreen = "theme" },
                        onOpenLanguage = { currentScreen = "language" }
                    )
                }

                "theme" -> {
                    SettingsThemeContent(
                        themeMode = viewModel.themeMode.collectAsState().value,
                        onUpdateTheme = { viewModel.setTheme(it) },
                        onBack = { currentScreen = "main" }
                    )
                }

                "language" -> {
                    Text("language")
                }
            }
        }
    }
}