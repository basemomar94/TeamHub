package org.zayn.teamhub.core.models

data class WorkDaySummary(
    val startOfDay: Long?,
    val endOfDay: Long?,
    val userId: String?,
    val date: String,
    val totalMinutesWorked: Long,
)