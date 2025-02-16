package org.zayn.teamhub.core.models

data class WorkDaySummary(
    val userId: String?,
    val date: String,
    val totalMinutesWorked: Long,
)