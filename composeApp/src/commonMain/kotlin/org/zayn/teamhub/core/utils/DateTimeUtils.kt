package org.zayn.teamhub.core.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun getCurrentTime() = Clock.System.now().epochSeconds

expect fun Long?.toLocalizedDateTime(): String

expect fun Long?.toLocalizedDate(): String
expect fun Long?.toWorkDuration(): String

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
