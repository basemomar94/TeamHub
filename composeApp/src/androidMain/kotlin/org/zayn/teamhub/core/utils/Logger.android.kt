package org.zayn.teamhub.core.utils

import android.util.Log

actual fun logDebug(tag: String, message: String) {
    Log.d(tag, message)
}

actual fun logInfo(tag: String, message: String) {
    Log.i(tag, message)
}

actual fun logError(tag: String, message: String) {
    Log.e(tag, message)
}

actual fun logError(tag: String, message: String, throwable: Throwable) {
    Log.e(tag, message, throwable)
}

actual fun logWarn(tag: String, message: String) {
    Log.w(tag, message)
}