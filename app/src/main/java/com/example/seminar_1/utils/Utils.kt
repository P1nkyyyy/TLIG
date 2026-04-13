package com.example.seminar_1.utils

import com.example.seminar_1.features.messages.data.model.MessageModel

private val monthsNominative = listOf(
    "LEDEN", "ÚNOR", "BŘEZEN", "DUBEN", "KVĚTEN", "ČERVEN",
    "ČERVENEC", "SRPEN", "ZÁŘÍ", "ŘÍJEN", "LISTOPAD", "PROSINEC"
)

private val monthsGenitive = listOf(
    "LEDNA", "ÚNORA", "BŘEZNA", "DUBNA", "KVĚTNA", "ČERVNA",
    "ČERVENCE", "SRPNA", "ZÁŘÍ", "ŘÍJNA", "LISTOPADU", "PROSINCE"
)

fun getYearFromDate(datum: String): String = datum.split(" ").lastOrNull() ?: "Unknown"

fun getDayFromDate(datum: String): String {
    return datum.trim().split(".").firstOrNull() ?: ""
}

fun getMonthFromDate(datum: String): String {
    val rawMonth = datum.trim().split(Regex("\\s+")).getOrNull(1)?.uppercase() ?: return "Unknown"

    val genitiveIndex = monthsGenitive.indexOf(rawMonth)
    return monthsNominative[genitiveIndex]
}

private fun getMonthPriority(month: String): Int {
    val index = monthsNominative.indexOf(month.uppercase())
    return if (index != -1) index % 12 else Int.MAX_VALUE
}

fun groupMessagesForModal(allMessages: List<MessageModel>): Map<String, Map<String, List<MessageModel>>> {
    return allMessages
        .groupBy { message -> getYearFromDate(removeNoteParser(message.date)) }
        .toSortedMap()
        .mapValues { (_, messagesInYear) ->
            messagesInYear
                .groupBy { message -> getMonthFromDate(removeNoteParser(message.date)) }
                .toSortedMap(compareBy { getMonthPriority(it) })
        }
}