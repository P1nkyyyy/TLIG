package com.example.seminar_1.features.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seminar_1.R
import com.example.seminar_1.features.messages.data.model.MessageModel
import com.example.seminar_1.utils.removeNoteParser
import com.example.seminar_1.utils.textParser


@Composable
fun MessageCardOnDay(
    message: MessageModel,
    onToggleArchive: () -> Unit,
    onClick: () -> Unit,
) {
    val cardBackground = Color(0xFF1A1F27)
    val borderColor = Color(0xFF2A3441)
    val dateColor = Color(0xFF64748B)       // slate-500
    val titleColor = Color(0xFFF1F5F9)      // slate-100
    val snippetColor = Color(0xFF94A3B8)     // slate-400
    val goldAccent = Color(0xFFC69C6D)

    val cardShape = RoundedCornerShape(12.dp)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(cardShape)
            .background(cardBackground)
            .clickable { onClick() }
            .border(1.dp, borderColor, cardShape)
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = removeNoteParser(message.date).uppercase(),
                color = goldAccent,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 1.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = removeNoteParser(message.title),
                color = titleColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Serif,
                lineHeight = 26.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = textParser(message.content),
                color = snippetColor,
                fontSize = 14.sp,
                fontFamily = FontFamily.Serif,
                fontStyle = FontStyle.Italic,
                lineHeight = 22.sp,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .padding(vertical = 4.dp, horizontal = 2.dp)
            ) {
                Text(
                    text = stringResource(R.string.home_message_card_read_more),
                    color = goldAccent,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Icon(
                    imageVector = Icons.Rounded.ChevronRight,
                    contentDescription = null,
                    tint = goldAccent,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        IconButton(
            onClick = onToggleArchive,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp)
        ) {
            Icon(
                imageVector = if (message.isArchived) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                contentDescription =
                    if (message.isArchived) stringResource(R.string.home_message_card_bookmark_marked)
                    else stringResource(R.string.home_message_card_bookmark_unmarked),
                tint = if (message.isArchived) goldAccent else dateColor
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF11141A)
@Composable
fun MessageCardOnDayPreview() {
    val mockMessage = MessageModel(
        id = 0,
        title = "Title",
        date = "31. ledna 2019",
        content = "Ó, Pane, veď mou duši po stezkách Věčného Života, veď Svou Církev k Jednotě, kéž je Tvé Poselství, se vším jeho bohatstvím, přijato celým Tvým stvořením!",
        isArchived = false,
        isCompleted = false,
        lastOpenedMessage = 0,
    )
    Box(modifier = Modifier.padding(16.dp)) {
        MessageCardOnDay(
            message = mockMessage,
            onToggleArchive = {},
            onClick = {}
        )
    }
}