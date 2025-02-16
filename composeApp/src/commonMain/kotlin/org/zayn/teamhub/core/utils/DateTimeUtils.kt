package org.zayn.teamhub.core.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.zayn.teamhub.core.utils.Logger.Companion.createLogger

fun getCurrentTime() = Clock.System.now().toEpochMilliseconds()

expect fun Long?.toLocalizedDateTime(): String

expect fun Long?.toLocalizedDate(): String

fun Long?.toWorkDuration(): String {

    if (this == null || this <= 0) return "0 mins"

    val hours = this / 60
    val minutes = this % 60

    return when {
        hours > 0 && minutes > 0 -> "$hours hr${if (hours > 1) "s" else ""} $minutes min${if (minutes > 1) "s" else ""}"
        hours > 0 -> "$hours hr${if (hours > 1) "s" else ""}"
        else -> "$minutes min${if (minutes > 1) "s" else ""}"
    }
}



fun getStartOfCurrentMonth(): Long {
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val startOfMonth = LocalDateTime(
        year = now.year,
        month = now.month,
        dayOfMonth = 1,
        hour = 0,
        minute = 0,
        second = 0,
        nanosecond = 0
    )
    return startOfMonth.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
}

fun getEndOfCurrentMonth(): Long {
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val lastDayOfMonth = getLastDayOfMonth(now.year, now.month)
    val endOfMonth = LocalDateTime(
        year = now.year,
        month = now.month,
        dayOfMonth = lastDayOfMonth,
        hour = 23,
        minute = 59,
        second = 59,
        nanosecond = 999_999_999
    )
    return endOfMonth.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
}

fun getStartOfDayMillis(timeZone: TimeZone = TimeZone.currentSystemDefault()): Long {
    val now = Clock.System.now().toLocalDateTime(timeZone).date
    val startOfDay = now.atStartOfDayIn(timeZone)
    return startOfDay.toEpochMilliseconds()
}

fun getEndOfDayMillis(timeZone: TimeZone = TimeZone.currentSystemDefault()): Long {
    val now = Clock.System.now().toLocalDateTime(timeZone).date
    val endOfDay = now.atTime(23, 59, 59, 999_999_999).toInstant(timeZone)
    return endOfDay.toEpochMilliseconds()
}

private fun getLastDayOfMonth(year: Int, month: Month): Int {
    return when (month) {
        Month.JANUARY, Month.MARCH, Month.MAY, Month.JULY, Month.AUGUST, Month.OCTOBER, Month.DECEMBER -> 31
        Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> 30
        Month.FEBRUARY -> if (isLeapYear(year)) 29 else 28
        else -> -1
    }
}

private fun isLeapYear(year: Int): Boolean {
    return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
}
