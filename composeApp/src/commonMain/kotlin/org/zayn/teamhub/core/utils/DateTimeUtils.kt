package org.zayn.teamhub.core.utils

import kotlinx.datetime.Clock

fun getCurrentTime() = Clock.System.now().epochSeconds

expect fun Long?.toLocalizedDateTime(): String

