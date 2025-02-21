package org.zayn.teamhub.core.utils

import org.zayn.teamhub.core.utils.Logger.Companion.createLogger

inline fun <reified T : Enum<T>> enumValueOf(name: String?, default: T): T {
    return try {
        if (name != null) {
            enumValueOf<T>(name)
        } else default
    } catch (e: IllegalArgumentException) {
        Logger.createLogger("enumValueOfOrNull").e("parsing enum error ${e.message}")
        default
    }
}
