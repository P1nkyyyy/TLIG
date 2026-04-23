package com.example.seminar_1.features.messages.components.settings_modal

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.seminar_1.features.messages.data.model.MessageThemeSettingsActions
import com.example.seminar_1.ui.theme.Seminar1Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsModal(
    onDismissRequest: () -> Unit,
    textSize: Int,
    backgroundColor: Color,
    contentColor: Color,
    lineHeight: Float,
    fontFamily: String,
    actions: MessageThemeSettingsActions,
) {
    var currentScreen by remember { mutableStateOf("main") }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 450.dp)
        ) {
            AnimatedContent(
                targetState = currentScreen,
                transitionSpec = {
                    if (targetState == "fonts") {
                        (slideInHorizontally { width -> width }).togetherWith(
                            slideOutHorizontally { width -> -width })
                    } else {
                        (slideInHorizontally { width -> -width }).togetherWith(
                            slideOutHorizontally { width -> width })
                    }
                },
                label = "modal_navigation"
            ) { screen ->
                when (screen) {
                    "main" -> {
                        MainSettingsContent(
                            textSize = textSize,
                            backgroundColor = backgroundColor,
                            contentColor = contentColor,
                            lineHeight = lineHeight,
                            fontFamily = fontFamily,
                            onClose = onDismissRequest,
                            onTextSizeChange = actions.onTextSizeChange,
                            onOpenFontSelect = { currentScreen = "fonts" },
                            onThemeColorsChange = actions.onThemeColorsChange,
                            onLineHeightChange = actions.onLineHeightChange,
                        )
                    }

                    "fonts" -> {
                        FontSelectContent(
                            selectedFont = fontFamily,
                            onBack = { currentScreen = "main" },
                            onFontChanged = actions.onFontChange
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingsModalPreview() {
    Seminar1Theme {
        Box(modifier = Modifier.padding(16.dp)) {
            SettingsModal(
                onDismissRequest = {},
                backgroundColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                textSize = 18,
                lineHeight = 1.5f,
                fontFamily = "Serif",
                actions = MessageThemeSettingsActions({}, {}, { _, _ -> }, {})
            )
        }
    }
}
