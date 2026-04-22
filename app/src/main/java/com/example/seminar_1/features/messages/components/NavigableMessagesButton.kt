package com.example.seminar_1.features.messages.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.seminar_1.R
import com.example.seminar_1.features.messages.components.messages_modal.MessagesModal
import com.example.seminar_1.features.messages.data.model.MessageModel
import com.example.seminar_1.features.settings.data.model.ThemeMode
import com.example.seminar_1.ui.theme.Seminar1Theme
import com.example.seminar_1.utils.removeNoteParser
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigableMessagesButton(
    currentMessage: MessageModel,
    messages: List<MessageModel>,
    onPreviousMessage: () -> Unit,
    onNextMessage: () -> Unit,
    backgroundColor: Color,
    contentColor: Color,
    loadMessage: (id: Int) -> Unit,
    onToggleArchive: (message: MessageModel) -> Unit,
) {
    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .wrapContentWidth()
            .padding(24.dp)
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(16.dp),
                clip = false
            )
            .border(
                width = 1.dp,
                color = contentColor.copy(alpha = 0.1f),
                shape = RoundedCornerShape(16.dp)
            )
            .background(backgroundColor, shape = RoundedCornerShape(16.dp)),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompositionLocalProvider(
            LocalContentColor provides contentColor
        ) {
            if (showSheet)
                MessagesModal(
                    onDismissRequest = {
                        scope.launch {
                            sheetState.hide()
                            showSheet = false
                        }
                    },
                    messages = messages,
                    loadMessage = loadMessage,
                    onToggleArchive = onToggleArchive,
                )

            IconButton(onClick = onPreviousMessage) {
                Icon(
                    imageVector = Icons.Rounded.ChevronLeft,
                    contentDescription = stringResource(R.string.messages_navigable_button_previous)
                )
            }

            Button(
                onClick = { showSheet = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = backgroundColor,
                    contentColor = contentColor
                ),
                shape = RectangleShape,
            ) {
                Text(removeNoteParser(currentMessage.date))
            }
            IconButton(onClick = onNextMessage) {
                Icon(
                    imageVector = Icons.Rounded.ChevronRight,
                    contentDescription = stringResource(R.string.messages_navigable_button_next)
                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun NavigableMessagesButtonPreview() {
    val mockMessage = MessageModel(
        id = 0,
        title = "Title",
        date = "31. ledna 2019",
        content = "Content",
        isArchived = false,
        isCompleted = false,
        lastOpenedMessage = 0,
    )
    Seminar1Theme(ThemeMode.LIGHT) {
        Box(Modifier.padding(20.dp)) {
            NavigableMessagesButton(
                currentMessage = mockMessage,
                onPreviousMessage = {},
                onNextMessage = {},
                messages = emptyList(),
                backgroundColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                loadMessage = {},
                onToggleArchive = {}
            )
        }
    }
}