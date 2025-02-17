package org.zayn.teamhub.core.models

data class WorkSession(
    val clockInTime: Long?,
    val clockOutTime: Long?,
    val clockInLocation: Location?,
    val clockOutLocation: Location?
)
