package org.zayn.teamhub.core.utils

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import org.zayn.teamhub.core.utils.Logger.Companion.createLogger

inline fun <reified T> String.safeDecodeFromString(
    jsonParser: Json = Json
): T? {
    return try {
        jsonParser.decodeFromString<T>(this)
    } catch (e: SerializationException) {
        Logger.createLogger("safeDecodeFromString").e("decoding excepting for $this ${e.message}")
        null
    }
}