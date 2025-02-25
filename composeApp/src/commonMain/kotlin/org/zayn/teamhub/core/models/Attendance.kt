package org.zayn.teamhub.core.models

import kotlinx.serialization.Serializable
import org.zayn.teamhub.core.base.EnumString

@Serializable
data class Attendance(
    val id: String,
    val userId: String,
    val createdAt: Long,
    @EnumString
    val type: String,
    val lat: Double? = 0.0,
    val long: Double? = 0.0,
    @EnumString
    val method: String = AttendanceMethod.MANUAL.name,
    val mobileId: String? = null,
    val deviceName: String? = null,
    val flag: List<String>? = listOf(),
    val isEdited: Boolean = false
)
