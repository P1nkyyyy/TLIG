package com.example.seminar_1.features.messages.components.search_modal

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seminar_1.features.messages.data.model.MessageModel
import com.example.seminar_1.features.messages.utils.getSearchSnippet
import com.example.seminar_1.features.messages.utils.highlightText
import com.example.seminar_1.utils.removeNoteParser
import com.example.seminar_1.utils.textParser

@Composable
fun SearchResultItem(
    result: MessageModel,
    searchQuery: String,
    onClick: () -> Unit
) {
    val parsedContent = textParser(result.content)
    val snippet = getSearchSnippet(parsedContent, searchQuery)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .border(3.dp, MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Text(
            text = removeNoteParser(result.date).uppercase(),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = highlightText(removeNoteParser(result.title), searchQuery),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 18.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = highlightText("„$snippet“", searchQuery),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp,
            fontFamily = FontFamily.Serif,
            fontStyle = FontStyle.Italic,
            lineHeight = 22.sp,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}