package com.example.seminar_1.features.messages.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seminar_1.R
import com.example.seminar_1.features.messages.data.model.MessageModel
import com.example.seminar_1.features.messages.data.model.MessageThemeSettingsUI
import com.example.seminar_1.utils.contentParser

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MessageReaderContent(
    message: MessageModel?,
    themeSettings: MessageThemeSettingsUI,
    initialParagraphIndex: Int = 0,
    noteText: String?,
    onLoadNote: (noteId: Int, messageId: Int) -> Unit,
    onAtBottomChange: (Boolean) -> Unit,
    onAtTopChange: (Boolean) -> Unit,
    onScrollIndexChange: (Int) -> Unit,
) {
    val paragraphs = remember(message?.content) {
        message?.content?.split("\n\n")?.filter { it.isNotBlank() } ?: emptyList()
    }

    val lazyListState = rememberLazyListState(
        initialFirstVisibleItemIndex = initialParagraphIndex
    )

    LaunchedEffect(message?.id) {
        if (message != null && initialParagraphIndex == 0) {
            lazyListState.scrollToItem(0)
        }
    }

    LaunchedEffect(
        lazyListState.canScrollForward,
        lazyListState.canScrollBackward
    ) {
        onAtTopChange(!lazyListState.canScrollBackward)
        onAtBottomChange(!lazyListState.canScrollForward)
    }

    val currentScrollIndex by rememberUpdatedState(lazyListState.firstVisibleItemIndex)
    val currentOnScrollIndexChange by rememberUpdatedState(onScrollIndexChange)

    DisposableEffect(message?.id) {
        onDispose {
            currentOnScrollIndexChange(currentScrollIndex)
        }
    }

    val selectedFontFamily = when (themeSettings.fontFamily) {
        "Serif" -> FontFamily.Serif
        "SansSerif" -> FontFamily.SansSerif
        "Monospace" -> FontFamily.Monospace
        "Roboto" -> FontFamily(Font(R.font.roboto_serif_regular))
        "Times New Roman" -> FontFamily(Font(R.font.times_new_roman))
        else -> FontFamily.Default
    }

    var showNoteModal by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(themeSettings.backgroundColor)
    ) {
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            item { Spacer(modifier = Modifier.height(80.dp)) }

            message?.let {
                item {
                    Text(
                        text = it.title.uppercase(),
                        style = MaterialTheme.typography.labelMedium.copy(
                            letterSpacing = 2.sp,
                            fontFamily = selectedFontFamily,
                            fontWeight = FontWeight.Bold
                        ),
                        color = themeSettings.contentColor.copy(alpha = 0.5f),
                        textAlign = TextAlign.Center,
                    )
                }

                item {
                    Text(
                        text = contentParser(
                            it.date,
                            themeSettings.contentColor,
                            { showNoteModal = true },
                            it.id,
                            onLoadNote
                        ),
                        style = MaterialTheme.typography.displayLarge.copy(
                            fontSize = (themeSettings.textSize * 1.8).sp,
                            fontFamily = selectedFontFamily,
                            lineHeight = (themeSettings.textSize * 2.2).sp
                        ),
                        color = themeSettings.contentColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 40.dp, top = 16.dp),
                    )
                }

                itemsIndexed(paragraphs) { index, paragraph ->
                    MessageContent(
                        rawText = paragraph,
                        contentColor = themeSettings.contentColor,
                        onNoteClicked = { showNoteModal = true },
                        messageId = it.id,
                        loadNote = onLoadNote,
                        fontFamily = selectedFontFamily,
                        textSize = themeSettings.textSize.sp,
                        lineHeight = themeSettings.lineHeight
                    )
                    if (index < paragraphs.size - 1) {
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }

                item { Spacer(modifier = Modifier.height(120.dp)) }
            }
        }

        noteText?.let {
            if (showNoteModal) {
                NoteModal(noteText = it, onClose = { showNoteModal = false })
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
    textSize: TextUnit,
    lineHeight: Float
) {
    val annotatedMessage = remember(rawText) {
        contentParser(rawText, contentColor, onNoteClicked, messageId, loadNote)
    }

    Text(
        text = annotatedMessage,
        style = MaterialTheme.typography.bodyLarge.copy(
            color = contentColor,
            fontSize = textSize,
            lineHeight = textSize * lineHeight,
            fontFamily = fontFamily
        ),
        modifier = Modifier.fillMaxWidth()
    )
}
