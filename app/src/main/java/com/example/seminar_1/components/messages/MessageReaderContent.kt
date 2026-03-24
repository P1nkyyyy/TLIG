package com.example.seminar_1.components.messages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.seminar_1.AppDatabase
import com.example.seminar_1.utils.contentParser
import com.example.seminar_1.utils.convertNoteParser

@Composable
fun MessageReaderContent() {
    var currentMessageId by remember { mutableStateOf(1) }

    // Database
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val dao = db.messagesDao()

    val message by dao.get(currentMessageId).collectAsState(initial = null)

    val scrollState = rememberScrollState()
    LaunchedEffect(key1 = message?.id) {
        scrollState.scrollTo(0)
    }

    // Floating Button Logic: Hide on scroll down, Show on scroll up or at bottom
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

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            message?.let {
                Text(
                    text = it.title,
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(bottom = 8.dp),
                )

                Text(
                    text = convertNoteParser(it.date),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(bottom = 32.dp),
                )

                Text(
                    text = contentParser(it.content),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(120.dp))
            }
        }

        message?.let { it ->
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
                    onPreviousClick = { if (currentMessageId > 1) { currentMessageId-- } },
                    onNextClick = { currentMessageId++ }
                )
            }
        }
    }
}
