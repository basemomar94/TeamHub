package org.zayn.teamhub.core.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.zayn.teamhub.core.models.Time
import org.zayn.teamhub.core.utils.Logger.Companion.createLogger

fun getCurrentTime() = Clock.System.now().toEpochMilliseconds()

expect fun Long?.toLocalizedDateTime(): String

expect fun Long?.toLocalizedDate(): String

fun Long?.toWorkDuration(): Time {

    if (this == null || this <= 0) return Time(0, 0)

    val hours = this / 60
    val minutes = this % 60

    return when {
        hours > 0 && minutes > 0 -> Time(hours.toInt(), minutes.toInt())
        hours > 0 -> Time(hours.toInt(), 0)
        else -> Time(0, minutes.toInt())
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

expect fun Long?.toLocalizedTime(): String?

fun calculateTimeDifference(clockInTime: String, userCheckInMillis: Long): Long {
    try {
        val (hour, minute) = clockInTime.split(":").map { it.toInt() }
        val companyClockInTime = LocalTime(hour, minute)

        val userCheckInInstant = Instant.fromEpochMilliseconds(userCheckInMillis)
        val userCheckInTime =
            userCheckInInstant.toLocalDateTime(TimeZone.currentSystemDefault()).time

        return companyClockInTime.minutesUntil(userCheckInTime, TimeZone.currentSystemDefault())
    } catch (e: Exception) {
        Logger.createLogger("calculateTimeDifference").e("parsing error ${e.message}")
        return 0
    }


}

fun LocalTime.minutesUntil(other: LocalTime, timeZone: TimeZone): Long {
    val thisMinutes = this.hour * 60 + this.minute
    val otherMinutes = other.hour * 60 + other.minute
    return (otherMinutes - thisMinutes).toLong()
}