package org.zayn.teamhub.core.models

data class Session(
    val id: String?,
    val createdAt: Long?,
    val location: Location?,
    val deviceName: String?,
    val flags: List<AttendanceFlag>?
)
