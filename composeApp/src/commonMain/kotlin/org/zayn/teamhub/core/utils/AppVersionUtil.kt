package org.zayn.teamhub.core.utils

import org.zayn.teamhub.core.utils.Logger.Companion.createLogger

expect fun getAppVersion(): String

expect fun getBuildVariant(): String

fun isVersionSupported(minSupportedVersion: String): Boolean {
    val logger = Logger.createLogger("isVersionSupported")
    val installedParts = getAppVersion().removeSuffix("-dev").split(".").map { it.toInt() }
    val minParts = minSupportedVersion.split(".").map { it.toInt() }
    logger.d("installedParts  $installedParts")
    logger.d("minParts   $minParts")
    val maxLength = maxOf(installedParts.size, minParts.size)

    for (i in 0 until maxLength) {
        val installed = installedParts.getOrElse(i) { 0 }
        val min = minParts.getOrElse(i) { 0 }

        if (installed > min) return true
        if (installed < min) return false
    }

    return true
}
