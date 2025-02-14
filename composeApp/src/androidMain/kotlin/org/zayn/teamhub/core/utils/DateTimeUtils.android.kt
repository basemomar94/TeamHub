package org.zayn.teamhub.core.utils

import java.text.DateFormat
import java.util.Date
import java.util.Locale

actual fun Long?.toLocalizedDateTime(): String {
    val locale = Locale.getDefault()
    val dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, locale)
    return dateFormat.format(Date(this?.times(1000) ?: 0))
}