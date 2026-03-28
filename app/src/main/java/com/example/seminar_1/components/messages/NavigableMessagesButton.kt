package com.example.seminar_1.components.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.seminar_1.R
import com.example.seminar_1.data_classes.MessageType
import com.example.seminar_1.ui.theme.Seminar1Theme
import com.example.seminar_1.utils.removeNoteParser
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigableMessagesButton(
    currentMessage: MessageType,
    messages: List<MessageType>,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit
) {
    var showSheet by remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    Row(
       modifier = Modifier
           .wrapContentWidth()
           .padding(24.dp)
           .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(16.dp)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompositionLocalProvider(
            LocalContentColor provides MaterialTheme.colorScheme.onPrimary
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
                )

            IconButton(onClick = onPreviousClick) {
                Icon(imageVector = Icons.Rounded.ChevronLeft, contentDescription = stringResource(R.string.messages_navigable_button_previous))
            }

            Button(
                onClick = { showSheet = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                )
            ) {
                Text(removeNoteParser(currentMessage.date))
            }
            IconButton(onClick = onNextClick) {
                Icon(imageVector = Icons.Rounded.ChevronRight, contentDescription = stringResource(R.string.messages_navigable_button_next))
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun NavigableMessagesButtonPreview() {
    val mockMessage = MessageType(id = 0, title = "Title", date = "Date", content = "Content", isArchived = false, isCompleted = false)
    Seminar1Theme(false) {
        NavigableMessagesButton(
            currentMessage = mockMessage,
            onPreviousClick = {},
            onNextClick = {},
            messages = emptyList()
        )
    }
}