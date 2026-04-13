package com.example.seminar_1.features.messages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.seminar_1.features.messages.components.MessageReaderContent
import com.example.seminar_1.features.messages.components.MessagesSettingsMenu
import com.example.seminar_1.features.messages.components.NavigableMessagesButton
import com.example.seminar_1.features.messages.components.settings_modal.SettingsModal
import com.example.seminar_1.features.messages.data.model.MessageThemeSettingsActions
import com.example.seminar_1.features.messages.data.model.MessageThemeSettingsUI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesScreen(
    messageId: Int,
    scrollToLast: Boolean = false,
    viewModel: MessagesViewModel = viewModel(factory = MessagesViewModel.Factory)
) {
    LaunchedEffect(messageId) {
        viewModel.loadMessage(messageId)
    }

    val message by viewModel.currentMessage.collectAsStateWithLifecycle()
    val allMessages by viewModel.allMessages.collectAsStateWithLifecycle()

    val screenState = rememberMessagesScreenState(
        allMessages = allMessages,
        currentMessageId = viewModel.currentMessageId,
        onMessageChange = { viewModel.loadMessage(it) }
    )

    var showSettingsModal by remember { mutableStateOf(false) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                val delta = available.y
                if (delta < -15) screenState.isVisible = false
                else if (delta > 15) screenState.isVisible = true
                return Offset.Zero
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(viewModel.backgroundColor)
            .nestedScroll(nestedScrollConnection)
            .statusBarsPadding()
            .navigationBarsPadding(),
    ) {
        HorizontalPager(
            state = screenState.pagerState,
            modifier = Modifier.fillMaxSize(),
            pageSpacing = 16.dp
        ) { page ->
            val messageForPage = allMessages[page]
            MessageReaderContent(
                message = messageForPage,
                themeSettings = MessageThemeSettingsUI(
                    textSize = viewModel.textSize,
                    lineHeight = viewModel.lineHeight,
                    fontFamily = viewModel.selectedFont,
                    backgroundColor = viewModel.backgroundColor,
                    contentColor = viewModel.contentColor
                ),
                initialParagraphIndex = if (messageForPage.id == messageId && scrollToLast) messageForPage.lastReadParagraph else 0,
                noteText = viewModel.currentNote.collectAsStateWithLifecycle().value,
                onLoadNote = { noteId, messageId -> viewModel.loadNote(noteId, messageId) },
                onAtBottomChange = { screenState.isAtBottom = it },
                onAtTopChange = { screenState.isAtTop = it },
                onScrollIndexChange = { index ->
                    viewModel.updateLastReadParagraph(messageForPage.id, index)
                }
            )
        }

        message?.let {
            AnimatedVisibility(
                visible = screenState.showOverlays,
                enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
                modifier = Modifier.align(Alignment.TopCenter)
            ) {
                MessagesSettingsMenu(
                    backgroundColor = viewModel.backgroundColor,
                    contentColor = viewModel.contentColor,
                    isArchived = it.isArchived,
                    onToggleArchive = { viewModel.updateArchive(it) },
                    onOpenSettingsModal = { showSettingsModal = true },
                    onOpenSearchModal = { /* TODO: Implement search */ },
                )
            }
        }

        message?.let {
            AnimatedVisibility(
                visible = screenState.showOverlays,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp)
            ) {
                NavigableMessagesButton(
                    currentMessage = it,
                    messages = allMessages,
                    onPreviousMessage = { viewModel.previousMessage() },
                    onNextMessage = { viewModel.nextMessage() },
                    backgroundColor = viewModel.backgroundColor,
                    contentColor = viewModel.contentColor,
                    loadMessage = { viewModel.loadMessage(it) },
                    onToggleArchive = { viewModel.updateArchive(it) },
                )
            }
        }

        if (showSettingsModal) {
            SettingsModal(
                onDismissRequest = { showSettingsModal = false },
                textSize = viewModel.textSize,
                lineHeight = viewModel.lineHeight,
                fontFamily = viewModel.selectedFont,
                actions = MessageThemeSettingsActions(
                    onTextSizeChange = { viewModel.updateTextSize(it) },
                    onFontChange = { viewModel.updateFont(it) },
                    onThemeColorsChange = { bg, content ->
                        viewModel.updateThemeColor(
                            bg,
                            content
                        )
                    },
                    onLineHeightChange = { viewModel.updateLineHeight(it) }
                )
            )
        }
    }
}
