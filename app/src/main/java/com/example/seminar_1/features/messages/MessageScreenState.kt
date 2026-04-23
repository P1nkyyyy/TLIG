package com.example.seminar_1.features.messages

import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.seminar_1.features.messages.data.model.MessageModel

class MessagesScreenState(
    val pagerState: PagerState,
    private val onMessageChange: (Int) -> Unit
) {
    var isVisible by mutableStateOf(true)
    var isAtTop by mutableStateOf(true)
    var isAtBottom by mutableStateOf(false)
    val showOverlays get() = isVisible || isAtTop || isAtBottom

    fun handlePageChange(allMessages: List<MessageModel>, currentId: Int) {
        if (allMessages.isNotEmpty()) {
            val targetId = allMessages[pagerState.currentPage].id
            if (targetId != currentId) {
                onMessageChange(targetId)
                isVisible = true
            }
        }
    }

    suspend fun syncPager(allMessages: List<MessageModel>, currentId: Int) {
        if (allMessages.isNotEmpty()) {
            val targetIndex = allMessages.indexOfFirst { it.id == currentId }
            if (targetIndex != -1 && targetIndex != pagerState.currentPage) {
                pagerState.scrollToPage(targetIndex)
            }
        }
    }
}

@Composable
fun rememberMessagesScreenState(
    allMessages: List<MessageModel>,
    currentMessageId: Int,
    onMessageChange: (Int) -> Unit
): MessagesScreenState {
    val startIndex = remember(allMessages, currentMessageId) {
        val index = allMessages.indexOfFirst { it.id == currentMessageId }
        if (index != -1) index else 0
    }

    val pagerState = rememberPagerState(
        initialPage = startIndex,
        pageCount = { allMessages.size }
    )

    val state = remember(pagerState) { MessagesScreenState(pagerState, onMessageChange) }

    LaunchedEffect(pagerState.currentPage) {
        state.handlePageChange(allMessages, currentMessageId)
    }

    LaunchedEffect(currentMessageId, allMessages) {
        state.syncPager(allMessages, currentMessageId)
    }
    return state
}