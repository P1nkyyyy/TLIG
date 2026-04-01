package com.example.seminar_1.screens.messages.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seminar_1.R
import com.example.seminar_1.data.model.MessageModel
import com.example.seminar_1.utils.contentParser

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MessageReaderContent(
    message: MessageModel?,
    messages: List<MessageModel>,
    textSize: Int,
    lineHeight: Float,
    fontFamily: String,
    backgroundColor: Color,
    contentColor: Color,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit,
    loadMessage: (id: Int) -> Unit,
    noteText: String?,
    loadNote: (id: Int, messageId: Int) -> Unit,
    handleTextEdit: () -> Unit,
    handleSearch: () -> Unit,
    isArchived: Boolean,
    handleArchive: () -> Unit,
) {
    val scrollState = rememberScrollState()

    // Reset scroll when message changes
    LaunchedEffect(message?.id) {
        scrollState.scrollTo(0)
    }

    var isVisible by remember { mutableStateOf(true) }
    var lastScrollOffset by remember { mutableIntStateOf(0) }

    LaunchedEffect(scrollState.value, scrollState.maxValue) {
        val currentScrollOffset = scrollState.value
        val isAtBottom = currentScrollOffset >= scrollState.maxValue && scrollState.maxValue > 0

        if (isAtBottom) {
            isVisible = true
        } else if (currentScrollOffset > lastScrollOffset && currentScrollOffset > 200) {
            isVisible = false
        } else if (currentScrollOffset < lastScrollOffset) {
            isVisible = true
        }
        lastScrollOffset = currentScrollOffset
    }

    val selectedFontFamily = when (fontFamily) {
        "Serif" -> FontFamily.Serif
        "SansSerif" -> FontFamily.SansSerif
        "Monospace" -> FontFamily.Monospace
        "Roboto" -> FontFamily(Font(R.font.roboto_serif_regular))
        "Times New Roman" -> FontFamily(Font(R.font.times_new_roman))
        else -> FontFamily.Default
    }

    var showNoteSheet by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            message?.let {
                // Spacer for Menu
                Spacer(modifier = Modifier.height(64.dp))

                Text(
                    text = it.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = (textSize * 1.2).sp,
                        fontFamily = selectedFontFamily,
                        fontWeight = FontWeight.Bold
                    ),
                    color = contentColor.copy(alpha = 0.6f),
                    textAlign = TextAlign.Center,
                )

                Text(
                    text = contentParser(
                        it.date,
                        contentColor,
                        { showNoteSheet = true },
                        it.id,
                        loadNote
                    ),
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontSize = (textSize * 2).sp,
                        fontFamily = selectedFontFamily,
                        fontWeight = FontWeight.Bold
                    ),
                    color = contentColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 48.dp, top = 12.dp),
                )

                MessageContent(
                    rawText = it.content,
                    contentColor = contentColor,
                    onNoteClicked = { showNoteSheet = true },
                    messageId = it.id,
                    loadNote = loadNote,
                    fontFamily = selectedFontFamily,
                )

                if (showNoteSheet) {
                    NoteModal(noteText, onClose = { showNoteSheet = false })
                }

                // Space for floating button through text content
                Spacer(modifier = Modifier.height(120.dp))
            }
        }

        // Top Menu with Animation
        AnimatedVisibility(
            visible = isVisible,
            enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            MessagesSettingsMenu(
                handleTextEdit = handleTextEdit,
                handleSearch = handleSearch,
                backgroundColor = backgroundColor,
                contentColor = contentColor,
                isArchived = isArchived,
                handleArchive = handleArchive
            )
        }

        message?.let {
            AnimatedVisibility(
                visible = isVisible,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp)
            ) {
                NavigableMessagesButton(
                    currentMessage = it,
                    messages = messages,
                    onPreviousClick = onPreviousClick,
                    onNextClick = onNextClick,
                    backgroundColor = backgroundColor,
                    contentColor = contentColor,
                    loadMessage = loadMessage,
                )
            }
        }
    }
}

@Composable
fun MessageContent(
    rawText: String,
    contentColor: Color,
    onNoteClicked: (String) -> Unit,
    messageId: Int,
    loadNote: (id: Int, messageId: Int) -> Unit,
    fontFamily: FontFamily,
) {
    val annotatedMessage = remember(rawText) {
        contentParser(rawText, contentColor, onNoteClicked, messageId, loadNote)
    }

    Text(
        text = annotatedMessage,
        style = MaterialTheme.typography.bodyLarge.copy(
            color = MaterialTheme.colorScheme.onSurface,
            lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.3f,
            fontFamily = fontFamily
        )
    )
}
