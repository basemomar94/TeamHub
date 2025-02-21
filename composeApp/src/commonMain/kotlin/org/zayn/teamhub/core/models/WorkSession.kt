package org.zayn.teamhub.core.models

data class WorkSession(
    val userId: String?,
    val clockIn: Session?,
    val clockOut: Session?
)
