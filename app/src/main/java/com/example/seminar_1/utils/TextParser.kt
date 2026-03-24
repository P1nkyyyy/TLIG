package com.example.seminar_1.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import java.util.regex.Pattern

@Composable
fun contentParser(rawText: String): AnnotatedString {
    return buildAnnotatedString {
        val jStyle = SpanStyle(color = MaterialTheme.colorScheme.onPrimary)
        val vStyle = SpanStyle(color = MaterialTheme.colorScheme.onBackground, fontStyle = FontStyle.Italic)
        val linkStyle = SpanStyle(color = Color.Yellow, fontSize = 12.sp)

        val matcher = Pattern.compile("<(/?[a-zA-Z]+)([^>]*)>").matcher(rawText)

        var lastIndex = 0

        while (matcher.find()) {
            val textBeforeTag = rawText.substring(lastIndex, matcher.start())
            append(textBeforeTag)

            val tag = matcher.group(1) ?: ""
            val attributes = matcher.group(2) ?: ""

            when (tag) {
                "j" -> pushStyle(jStyle)
                "/j" -> pop()

                "v" -> pushStyle(vStyle)
                "/v" -> pop()

                "n" -> {
                    val idMatcher = Pattern.compile("id=\"([^\"]+)\"").matcher(attributes)
                    if (idMatcher.find()) {
                        val noteId = idMatcher.group(1) ?: ""

                        pushStringAnnotation(tag = "FOOTNOTE", annotation = noteId)
                        pushStyle(linkStyle)
                        append(noteId)
                        pop()
                        pop()
                    }
                }
            }
            lastIndex = matcher.end()
        }

        if (lastIndex < rawText.length) {
            append(rawText.substring(lastIndex))
        }
    }
}

fun textParser(rawText: String): String {
    val withoutDate = rawText.replace(Regex("(?s)<h>.*?</h>"), "")
    val withoutTags = withoutDate.replace(Regex("<[^>]+>"), "")
    val singleLineText = withoutTags.replace(Regex("\\s+"), " ")

    return singleLineText.trim()
}

fun convertNoteParser(text: String): String {
    val regex = Regex("<n id=\"([^\"]+)\"></n>")

    return text.replace(regex, "[$1]")
}

fun removeNoteParser(text: String): String {
    val regex = Regex("<n id=\"([^\"]+)\"></n>")

    return text.replace(regex, "")
}

