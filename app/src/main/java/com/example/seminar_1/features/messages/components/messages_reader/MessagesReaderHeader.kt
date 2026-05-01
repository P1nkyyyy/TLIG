package com.example.seminar_1.features.messages.components.messages_reader

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seminar_1.features.messages.data.model.MessageModel
import com.example.seminar_1.features.messages.data.model.MessageThemeSettingsUI
import com.example.seminar_1.utils.contentParser

@Composable
fun MessageReaderHeader(
    message: MessageModel,
    themeSettings: MessageThemeSettingsUI,
    fontFamily: FontFamily,
    onNoteClick: (String) -> Unit,
    onLoadNote: (Int, Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message.title.uppercase(),
            style = MaterialTheme.typography.labelMedium.copy(
                letterSpacing = 2.sp,
                fontFamily = fontFamily,
                fontSize = (themeSettings.textSize * 0.7).sp,
                fontWeight = FontWeight.Bold
            ),
            color = themeSettings.contentColor.copy(alpha = 0.5f),
            textAlign = TextAlign.Center,
        )

        Text(
            text = contentParser(
                message.date,
                themeSettings.contentColor,
                onNoteClick,
                message.id,
                onLoadNote
            ),
            style = MaterialTheme.typography.displayLarge.copy(
                fontSize = (themeSettings.textSize * 1.8).sp,
                fontFamily = fontFamily,
                lineHeight = (themeSettings.textSize * 2.2).sp
            ),
            color = themeSettings.contentColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 40.dp, top = 16.dp),
        )
    }
}