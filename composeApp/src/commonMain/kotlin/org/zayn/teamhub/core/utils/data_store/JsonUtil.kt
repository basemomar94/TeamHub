package org.zayn.teamhub.core.utils.data_store

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

inline fun <reified T> encodeToString(data: T): String {
    return Json.encodeToString(data)
}

inline fun <reified T> decodeFromString(json: String?): T? {
    return json?.let { Json.decodeFromString<T>(it) }
}