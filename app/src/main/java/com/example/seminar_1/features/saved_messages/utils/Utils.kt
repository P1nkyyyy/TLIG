package com.example.seminar_1.features.saved_messages.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun formatTimestamp(timestamp: Long?): String {
    if (timestamp == null) return ""

    val now = Calendar.getInstance()
    val time = Calendar.getInstance().apply { timeInMillis = timestamp }

    val diffMillis = now.timeInMillis - time.timeInMillis
    val diffDays = diffMillis / (24 * 60 * 60 * 1000)

    return when {
        isSameDay(now, time) -> "Dnes"
        isYesterday(now, time) -> "Včera"
        isDayBeforeYesterday(now, time) -> "Předevčírem"
        diffDays < 7 -> {
            val sdf = SimpleDateFormat("EEEE", Locale("cs", "CZ"))
            sdf.format(Date(timestamp)).replaceFirstChar { it.uppercase() }
        }

        else -> {
            val sdf = SimpleDateFormat("d. MMMM yyyy", Locale("cs", "CZ"))
            sdf.format(Date(timestamp))
        }
    }
}

private fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
}

private fun isYesterday(now: Calendar, then: Calendar): Boolean {
    val yesterday = now.clone() as Calendar
    yesterday.add(Calendar.DAY_OF_YEAR, -1)
    return isSameDay(yesterday, then)
}

private fun isDayBeforeYesterday(now: Calendar, then: Calendar): Boolean {
    val dayBefore = now.clone() as Calendar
    dayBefore.add(Calendar.DAY_OF_YEAR, -2)
    return isSameDay(dayBefore, then)
}