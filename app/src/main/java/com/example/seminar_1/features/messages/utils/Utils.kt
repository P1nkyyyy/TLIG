package com.example.seminar_1.features.messages.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight

@Composable
fun highlightText(originalText: String, query: String): AnnotatedString {
    if (query.isBlank()) return AnnotatedString(originalText)

    val builder = AnnotatedString.Builder(originalText)
    val lowerText = originalText.lowercase()
    val lowerQuery = query.lowercase()

    var startIndex = 0
    while (startIndex < lowerText.length) {
        val index = lowerText.indexOf(lowerQuery, startIndex)
        if (index < 0) break

        builder.addStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                background = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
            ),
            start = index,
            end = index + query.length
        )
        startIndex = index + query.length
    }
    return builder.toAnnotatedString()
}

fun getSearchSnippet(text: String, query: String, contextLength: Int = 50): String {
    if (query.isBlank()) return text

    val index = text.indexOf(query, ignoreCase = true)
    if (index == -1 || index < contextLength) return text

    val start = index - contextLength
    val prefix = "..."

    val firstSpace = text.indexOf(' ', start)
    val actualStart = if (firstSpace != -1 && firstSpace < index) firstSpace + 1 else start

    return prefix + text.substring(actualStart)
}