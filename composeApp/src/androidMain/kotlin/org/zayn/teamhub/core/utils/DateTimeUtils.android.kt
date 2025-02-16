package org.zayn.teamhub.core.utils

import java.text.DateFormat
import java.util.Date
import java.util.Locale

actual fun Long?.toLocalizedDateTime(): String {
    val locale = Locale.getDefault()
    val dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, locale)
    return dateFormat.format(Date(this ?: 0))
}



actual fun Long?.toLocalizedDate(): String {
    val locale = Locale.getDefault()
    val dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale)
    return dateFormat.format(Date(this ?: 0))
}