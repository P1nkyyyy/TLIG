package com.example.seminar_1.components.messages

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesBottomModal(
    onDismissRequest: () -> Unit,
    textSize: Int,
    onTextSize: (Int) -> Unit,
    fontFamily: String,
    onFontSelected: (String) -> Unit,
    onBackgroundColor: (Color, Color) -> Unit
) {
    var currentScreen by remember { mutableStateOf("main") }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest
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
                            onClose = onDismissRequest,
                            textSize = textSize,
                            onTextSize = onTextSize,
                            fontFamily = fontFamily,
                            onOpenFontSelect = { currentScreen = "fonts" },
                            onBackgroundColor = onBackgroundColor
                        )
                    }
                    "fonts" -> {
                        FontSelectContent(
                            selectedFont = fontFamily,
                            onBack = { currentScreen = "main" },
                            onFontSelected = {
                                onFontSelected(it)
                            }
                        )
                    }
                }
            }
        }
    }
}
