package com.example.seminar_1.features.messages.components.messages_reader

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.seminar_1.R
import com.example.seminar_1.features.messages.data.model.MessageThemeSettingsUI
import com.example.seminar_1.utils.contentParser

@Composable
fun MessagesReaderParagraph(
    paragraph: String,
    messageId: Int,
    themeSettings: MessageThemeSettingsUI,
    fontFamily: FontFamily,
    onNoteClick: (String) -> Unit,
    onLoadNote: (Int, Int) -> Unit
) {
    val inlineContent = mapOf(
        "ichtis" to InlineTextContent(
            Placeholder(
                width = 1.7.em,
                height = 1.em,
                placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
            )
        ) {
            Image(
                painter = painterResource(id = R.drawable.tlig_ichtis),
                contentDescription = "Ichtis",
                modifier = Modifier.fillMaxSize()
            )
        }
    )

    Text(
        text = contentParser(
            paragraph,
            themeSettings.contentColor,
            onNoteClick,
            messageId,
            onLoadNote
        ),
        inlineContent = inlineContent,
        color = themeSettings.contentColor,
        style = MaterialTheme.typography.bodyLarge.copy(
            fontFamily = fontFamily,
            fontSize = themeSettings.textSize.sp,
            lineHeight = themeSettings.textSize.sp * themeSettings.lineHeight,
        ),
        modifier = Modifier.fillMaxWidth()
    )
}