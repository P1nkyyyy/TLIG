package com.example.seminar_1.features.messages.components.messages_reader

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.example.seminar_1.features.messages.components.NoteModal
import com.example.seminar_1.features.messages.data.model.MessageModel
import com.example.seminar_1.features.messages.data.model.MessageThemeSettingsUI
import com.example.seminar_1.features.messages.utils.rememberMessageFontFamily
import com.example.seminar_1.ui.theme.spacing

@Composable
fun MessagesReader(
    message: MessageModel?,
    themeSettings: MessageThemeSettingsUI,
    initialParagraphIndex: Int = 0,
    noteText: String?,
    onLoadNote: (noteId: Int, messageId: Int) -> Unit,
    onLoadMessage: (Int) -> Unit,
    onAtBottomChange: (Boolean) -> Unit,
    onAtTopChange: (Boolean) -> Unit,
    onScrollIndexChange: (Int) -> Unit,
) {
    val state = rememberMessagesReaderState(
        message = message,
        initialIndex = initialParagraphIndex,
        onAtTopChange = onAtTopChange,
        onAtBottomChange = onAtBottomChange,
        onScrollIndexChange = onScrollIndexChange
    )

    val selectedFontFamily = rememberMessageFontFamily(themeSettings.fontFamily)
    val uriHandler = LocalUriHandler.current

    val handleNoteClick: (String) -> Unit = { tag ->
        when {
            tag.startsWith("bible:") -> {
                tag.removePrefix("bible:").takeIf { it.isNotBlank() }
                    ?.let { uriHandler.openUri(it) }
            }

            tag.startsWith("message:") -> {
                tag.removePrefix("message:").toIntOrNull()?.let {
                    state.showNoteModal = false
                    onLoadMessage(it)
                }
            }

            else -> state.showNoteModal = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(themeSettings.backgroundColor)
    ) {
        LazyColumn(
            state = state.lazyListState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.base3),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            item { Spacer(modifier = Modifier.height(80.dp)) }

            message?.let { msg ->
                item {
                    MessageReaderHeader(
                        message = msg,
                        themeSettings = themeSettings,
                        fontFamily = selectedFontFamily,
                        onNoteClick = handleNoteClick,
                        onLoadNote = onLoadNote
                    )
                }

                itemsIndexed(state.paragraphs) { index, paragraph ->
                    MessagesReaderParagraph(
                        paragraph = paragraph,
                        messageId = msg.id,
                        themeSettings = themeSettings,
                        fontFamily = selectedFontFamily,
                        onNoteClick = handleNoteClick,
                        onLoadNote = onLoadNote
                    )
                    if (index < state.paragraphs.size - 1) {
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }

                item { Spacer(modifier = Modifier.height(120.dp)) }
            }
        }

        if (state.showNoteModal && noteText != null) {
            NoteModal(
                noteType = "Poznámka",
                noteText = noteText,
                onClose = { state.showNoteModal = false },
                onNoteClicked = handleNoteClick,
            )
        }
    }
}
