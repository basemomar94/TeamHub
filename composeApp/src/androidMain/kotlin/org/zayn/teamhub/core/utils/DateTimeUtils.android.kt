package org.zayn.teamhub.core.utils

import java.text.DateFormat
import java.util.Date
import java.util.Locale

actual fun Long?.toLocalizedDateTime(): String {
    val locale = Locale.getDefault()
    val dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, locale)
    return dateFormat.format(Date(this?.times(1000) ?: 0))
}

actual fun Long?.toWorkDuration(): String {
    val hours = this?.div(60)
    val minutes = this?.rem(60)
    return if (hours != null) {
        return if (hours > 0) {
            "$hours hrs $minutes mins"
        } else {
            "$minutes mins"
        }
    } else {
        ""
    }
}

actual fun Long?.toLocalizedDate(): String {
    val locale = Locale.getDefault()
    val dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale)
    return dateFormat.format(Date(this?.times(1000) ?: 0))
}