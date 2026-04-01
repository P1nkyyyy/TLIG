package com.example.seminar_1.screens.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.seminar_1.screens.messages.components.MessageReaderContent
import com.example.seminar_1.screens.messages.components.settings.SettingsModal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesScreen(
    viewModel: MessagesViewModel = viewModel(factory = MessagesViewModel.Factory)
) {
    var showSheet by remember { mutableStateOf(false) }

    val currentMessage by viewModel.currentMessage.collectAsState()
    val allMessages by viewModel.allMessages.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(viewModel.backgroundColor)
            .navigationBarsPadding()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        if (showSheet) {
            SettingsModal(
                onDismissRequest = { showSheet = false },
                textSize = viewModel.textSize,
                onTextSize = { textSize -> viewModel.updateTextSize(textSize) },
                fontFamily = viewModel.selectedFont,
                onFontSelected = { fontFamily -> viewModel.updateFont(fontFamily) },
                onBackgroundColor = { backgroundColor, contentColor ->
                    viewModel.updateBackground(
                        backgroundColor,
                        contentColor
                    )
                },
            )
        }

        MessageReaderContent(
            message = currentMessage,
            messages = allMessages,
            textSize = viewModel.textSize,
            lineHeight = viewModel.lineHeight,
            fontFamily = viewModel.selectedFont,
            backgroundColor = viewModel.backgroundColor,
            contentColor = viewModel.contentColor,
            onNextClick = { viewModel.nextMessage() },
            onPreviousClick = { viewModel.previousMessage() },
            loadMessage = { id -> viewModel.loadMessage(id) },
            noteText = viewModel.currentNote.collectAsState().value,
            loadNote = { id, messageId -> viewModel.loadNote(id, messageId) },
            handleTextEdit = { showSheet = true },
            handleSearch = { /* TODO: Implement search */ },
            isArchived = viewModel.isArchived,
            handleArchive = { viewModel.updateArchive() }
        )
    }
}
