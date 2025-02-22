package org.zayn.teamhub.core.models

import kotlinx.serialization.Serializable
import org.zayn.teamhub.core.base.EnumString

@Serializable
data class Company(
    val id: String? = "",
    val name: String? = "",
    val logoUrl: String? = "",
    val lat: Double?,
    val lon: Double?,
    val workingHours: Long?,
    @EnumString
    val attendancePolicy: String?,
    val distanceTolerance: Double?,
    val lateTolerance: Long?,
    val clockInTime: String?,
)
