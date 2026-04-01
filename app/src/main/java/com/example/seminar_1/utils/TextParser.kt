package com.example.seminar_1.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import java.util.regex.Pattern

/**
 * Original parser that highlights the word preceding the note tag.
 */
fun contentParser(
    rawText: String,
    contentColor: Color,
    onNoteClicked: (String) -> Unit,
    messageId: Int,
//    noteText: String?,
    loadNote: (id: Int, messageId: Int) -> Unit,
): AnnotatedString {
    return internalParser(rawText, contentColor, onNoteClicked, messageId, loadNote)
}

private fun internalParser(
    rawText: String,
    contentColor: Color,
    onNoteClicked: (String) -> Unit,
    messageId: Int,
    loadNote: (id: Int, messageId: Int) -> Unit,
): AnnotatedString {
    val cleanText = rawText.replace(Regex("(?s)<h>.*?</h>"), "")

    return buildAnnotatedString {
        // Define styles based on contentColor
        val jesusStyle = SpanStyle(color = contentColor)
        val vasulaStyle = SpanStyle(
            fontStyle = FontStyle.Italic,
            color = contentColor.copy(alpha = 0.8f)
        )
        val highlightStyle = SpanStyle(
            color = Color(0xFFB57C2C),
            fontWeight = FontWeight.Bold,
        )

        val tagMatcher = Pattern.compile("<(/?[a-zA-Z]+)([^>]*)>").matcher(cleanText)
        var lastIndex = 0

        while (tagMatcher.find()) {
            // Append text before the tag
            append(cleanText.substring(lastIndex, tagMatcher.start()))

            val tag = tagMatcher.group(1) ?: ""
            val attributes = tagMatcher.group(2) ?: ""

            when (tag) {
                "j" -> pushStyle(jesusStyle)
                "/j" -> pop()
                "v" -> pushStyle(vasulaStyle)
                "/v" -> pop()
                "n" -> {
                    val idMatcher = Pattern.compile("id=\"([^\"]+)\"").matcher(attributes)
                    if (idMatcher.find()) {
                        val noteIdString = idMatcher.group(1) ?: ""
                        val noteId = noteIdString.toIntOrNull() ?: 0

                        // Original logic: Highlight the word preceding the <n> tag
                        val currentText = this.toAnnotatedString().text
                        val wordEnd = currentText.length
                        val wordStart = currentText.indexOfLast { it.isWhitespace() }.let {
                            if (it == -1) 0 else it + 1
                        }

                        if (wordStart < wordEnd) {
                            addLink(
                                clickable = LinkAnnotation.Clickable(
                                    tag = noteIdString,
                                    styles = TextLinkStyles(style = highlightStyle),
                                    linkInteractionListener = {
                                        onNoteClicked(noteIdString)
                                        loadNote(noteId, messageId)
                                    }
                                ),
                                start = wordStart,
                                end = wordEnd
                            )
                        }
                    }
                }
            }
            lastIndex = tagMatcher.end()
        }

        // Append remaining text
        if (lastIndex < cleanText.length) {
            append(cleanText.substring(lastIndex))
        }
    }
}

fun textParser(rawText: String): String {
    val withoutTags = rawText.replace(Regex("<[^>]+>"), "")
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
