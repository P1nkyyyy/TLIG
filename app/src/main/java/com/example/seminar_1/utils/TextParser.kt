package com.example.seminar_1.utils

import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import java.util.regex.Pattern

/**
 * Hlavní parser pro texty poselství.
 */
fun contentParser(
    rawText: String,
    contentColor: Color,
    onNoteClicked: (String) -> Unit,
    messageId: Int,
    loadNote: (id: Int, messageId: Int) -> Unit,
): AnnotatedString = parseAnnotatedText(
    rawText = rawText,
    contentColor = contentColor,
    onNoteClicked = onNoteClicked,
    messageId = messageId,
    loadNote = loadNote,
    isFullParser = true
)

/**
 * Parser pro poznámky.
 * Podporuje jen podtržení, biblické odkazy a odkazy na poselství.
 */
fun noteContentParser(
    rawText: String,
    contentColor: Color,
    onNoteClicked: (String) -> Unit
): AnnotatedString = parseAnnotatedText(
    rawText = rawText,
    contentColor = contentColor,
    onNoteClicked = onNoteClicked,
    isFullParser = false
)

/**
 * Odstraní všechny tagy a vrátí čistý text
 */
fun textParser(rawText: String): String {
    val withoutTags = rawText.replace(Regex("<[^>]+>"), "")
    return withoutTags.replace(Regex("\\s+"), " ").trim()
}

/**
 * Odstraní jen tagy poznámek <n>, ale ostatní formátování nechá (používá se pro nadpisy).
 */
fun removeNoteParser(text: String): String {
    return text.replace(Regex("<n id=\"([^\"]+)\"></n>"), "")
}

private fun parseAnnotatedText(
    rawText: String,
    contentColor: Color,
    onNoteClicked: (String) -> Unit,
    messageId: Int = 0,
    loadNote: ((Int, Int) -> Unit)? = null,
    isFullParser: Boolean
): AnnotatedString {
    // Odstranění pomocných tagů <h>
    val cleanText = rawText.replace(Regex("(?s)<h>.*?</h>"), "")

    return buildAnnotatedString {
        // Definice stylů
        val jesusStyle = SpanStyle(color = contentColor)
        val vasulaStyle =
            SpanStyle(fontStyle = FontStyle.Italic, color = contentColor.copy(alpha = 0.8f))
        val highlightStyle = SpanStyle(color = Color(0xFFB57C2C), fontWeight = FontWeight.Bold)
        val underlineStyle = SpanStyle(textDecoration = TextDecoration.Underline)
        val linkStyle = SpanStyle(color = Color(0xFFB57C2C), fontWeight = FontWeight.Bold)

        val tagMatcher = Pattern.compile("<(/?[a-zA-Z]+)([^>]*)>").matcher(cleanText)
        var lastIndex = 0

        // Pomocné proměnné pro odkazy
        var bibleStart = -1
        var messageStart = -1
        var currentHref = ""
        var currentMessageId = ""

        while (tagMatcher.find()) {
            append(cleanText.substring(lastIndex, tagMatcher.start()))

            val tag = tagMatcher.group(1)?.lowercase() ?: ""
            val attributes = tagMatcher.group(2) ?: ""

            when (tag) {
                // Společné tagy pro oba parsery
                "u" -> pushStyle(underlineStyle)
                "/u" -> pop()

                "b" -> {
                    val hrefMatcher = Pattern.compile("href=\"([^\"]+)\"").matcher(attributes)
                    val rawHref = if (hrefMatcher.find()) hrefMatcher.group(1) ?: "" else ""
                    currentHref = rawHref.replace("amp;", "")
                    pushStyle(linkStyle)
                    bibleStart = length
                    append("📖 ")
                }

                "/b" -> {
                    pop()
                    val bibleEnd = length
                    if (bibleStart != -1 && bibleStart < bibleEnd) {
                        addLink(
                            clickable = LinkAnnotation.Clickable(
                                tag = "bible:$currentHref",
                                styles = TextLinkStyles(style = linkStyle),
                                linkInteractionListener = { onNoteClicked("bible:$currentHref") }
                            ),
                            start = bibleStart,
                            end = bibleEnd
                        )
                    }
                    bibleStart = -1
                }

                "m" -> {
                    val idMatcher = Pattern.compile("id=\"([^\"]+)\"").matcher(attributes)
                    currentMessageId = if (idMatcher.find()) idMatcher.group(1) ?: "" else ""
                    pushStyle(linkStyle)
                    messageStart = length
                }

                "/m" -> {
                    pop()
                    val messageEnd = length
                    if (messageStart != -1 && messageStart < messageEnd) {
                        addLink(
                            clickable = LinkAnnotation.Clickable(
                                tag = "message:$currentMessageId",
                                styles = TextLinkStyles(style = linkStyle),
                                linkInteractionListener = { onNoteClicked("message:$currentMessageId") }
                            ),
                            start = messageStart,
                            end = messageEnd
                        )
                    }
                    messageStart = -1
                }

                // Tagy pouze pro HLAVNÍ parser
                "j" -> if (isFullParser) pushStyle(jesusStyle)
                "/j" -> if (isFullParser) pop()
                "v" -> if (isFullParser) pushStyle(vasulaStyle)
                "/v" -> if (isFullParser) pop()
                "center" -> if (isFullParser) pushStyle(ParagraphStyle(textAlign = TextAlign.Center))
                "/center" -> if (isFullParser) pop()
                "fish" -> if (isFullParser) appendInlineContent("ichtis", "[ichtis]")

                "n" -> if (isFullParser && loadNote != null) {
                    val idMatcher = Pattern.compile("id=\"([^\"]+)\"").matcher(attributes)
                    if (idMatcher.find()) {
                        val noteIdString = idMatcher.group(1) ?: ""
                        val noteId = noteIdString.toIntOrNull() ?: 0

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

        if (lastIndex < cleanText.length) {
            append(cleanText.substring(lastIndex))
        }
    }
}