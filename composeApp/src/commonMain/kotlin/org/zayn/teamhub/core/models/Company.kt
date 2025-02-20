package org.zayn.teamhub.core.models

import kotlinx.serialization.Serializable
import org.zayn.teamhub.core.base.EnumString

@Serializable
data class Company(
    val id: String? = "",
    val name: String? = "",
    val logoUrl: String? = "",
    val lat: Double? = null,
    val lon: Double? = null,
    val workingHours: Long? = null,
    @EnumString
    val attendancePolicy: String? = null,
    val distanceTolerance: Double? = null,
    val lateTolerance: Long? = null,
    val clockInTime: String,
)
