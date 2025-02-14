package org.zayn.teamhub.core.models

import dev.gitlive.firebase.firestore.Timestamp
import kotlinx.serialization.Serializable

@Serializable
data class Attendance(
    val userId: String,
    val createdAt: Long,
    val type: String,
    val lat: Double? = 0.0,
    val long: Double? = 0.0
)
