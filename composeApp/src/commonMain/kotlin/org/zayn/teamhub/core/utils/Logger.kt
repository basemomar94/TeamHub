package org.zayn.teamhub.core.utils

class Logger(private val tag: String) {

    fun d(message: String) {
        logDebug(tag, message)
    }

    fun i(message: String) {
        logInfo(tag, message)
    }

    fun e(message: String) {
        logError(tag, message)
    }

    fun e(message: String, throwable: Throwable) {
        logError(tag, message, throwable)
    }

    fun w(message: String) {
        logWarn(tag, message)
    }

    companion object {
        inline fun <reified T : Any> T.createLogger(customTag: String? = null): Logger {
            val tag = "-- " + (customTag ?: T::class.simpleName ?: "DefaultTag")
            return Logger(tag)
        }
    }
}

// Define expected functions for platform-specific logging
expect fun logDebug(tag: String, message: String)
expect fun logInfo(tag: String, message: String)
expect fun logError(tag: String, message: String)
expect fun logError(tag: String, message: String, throwable: Throwable)
expect fun logWarn(tag: String, message: String)

