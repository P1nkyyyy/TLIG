package com.example.seminar_1.components.messages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import com.example.seminar_1.data_classes.MessageType
import com.example.seminar_1.utils.contentParser
import com.example.seminar_1.utils.convertNoteParser

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MessageReaderContent(
    message: MessageType?,
    messages: List<MessageType>,
    textSize: Int,
    lineHeight: Float,
    fontFamily: String,
    backgroundColor: Color,
    contentColor: Color,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    println("Aktuální barva textu: $contentColor")

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

    val selectedFontFamily = when(fontFamily) {
        "Serif" -> FontFamily.Serif
        "SansSerif" -> FontFamily.SansSerif
        "Monospace" -> FontFamily.Monospace
        "Roboto" -> FontFamily(Font(R.font.roboto_serif_regular))
        "Times New Roman" -> FontFamily(Font(R.font.times_new_roman))
        else -> FontFamily.Default
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(backgroundColor)) {
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
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = (textSize * 1.2).sp,
                        fontFamily = selectedFontFamily,
                        fontWeight = FontWeight.Bold
                    ),
                    color = contentColor.copy(alpha = 0.6f),
                    textAlign = TextAlign.Center,
                )

                Text(
                    text = convertNoteParser(it.date),
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontSize = (textSize * 2).sp,
                        fontFamily = selectedFontFamily,
                        fontWeight = FontWeight.Bold
                    ),
                    color = contentColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 48.dp, top = 12.dp),
                )

                Text(
                    text = contentParser(it.content, contentColor),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = textSize.sp,
                        lineHeight = (textSize * lineHeight).sp,
                        fontFamily = selectedFontFamily
                    ),
                    color = contentColor,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(120.dp))
            }
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
                    onNextClick = onNextClick
                )
            }
        }
    }
}
