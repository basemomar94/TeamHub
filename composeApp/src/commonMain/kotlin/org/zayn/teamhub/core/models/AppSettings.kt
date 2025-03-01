package org.zayn.teamhub.core.models

import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    val minimumVersion: String?
)
