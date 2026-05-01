package com.example.seminar_1.features.messages.components.messages_reader

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import com.example.seminar_1.features.messages.data.model.MessageModel

class MessagesReaderState(
    val lazyListState: LazyListState,
    val message: MessageModel?,
) {
    var showNoteModal by mutableStateOf(false)

    val isAtTop by derivedStateOf { !lazyListState.canScrollBackward }
    val isAtBottom by derivedStateOf { !lazyListState.canScrollForward }

    val paragraphs by derivedStateOf {
        message?.content?.split("\n\n")?.filter { it.isNotBlank() } ?: emptyList()
    }
}

@Composable
fun rememberMessagesReaderState(
    message: MessageModel?,
    initialIndex: Int = 0,
    onAtTopChange: (Boolean) -> Unit,
    onAtBottomChange: (Boolean) -> Unit,
    onScrollIndexChange: (Int) -> Unit
): MessagesReaderState {
    val lazyListState = rememberLazyListState(initialFirstVisibleItemIndex = initialIndex)

    val state = remember(lazyListState, message) {
        MessagesReaderState(lazyListState, message)
    }

    // Synchronizace stavu scrollu s okolím (TopBar/BottomBar)
    LaunchedEffect(state.isAtTop, state.isAtBottom) {
        onAtTopChange(state.isAtTop)
        onAtBottomChange(state.isAtBottom)
    }

    // Reset pozice při změně zprávy (pokud nepokračujeme ve čtení)
    LaunchedEffect(message?.id) {
        if (message != null && initialIndex == 0) {
            lazyListState.scrollToItem(0)
        }
    }

    // Uložení pozice při opuštění obrazovky
    val currentOnScrollIndexChange by rememberUpdatedState(onScrollIndexChange)
    DisposableEffect(message?.id) {
        onDispose {
            currentOnScrollIndexChange(lazyListState.firstVisibleItemIndex)
        }
    }

    return state
}
