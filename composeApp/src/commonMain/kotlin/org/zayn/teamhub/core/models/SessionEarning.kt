package org.zayn.teamhub.core.models

data class SessionEarning(
    val id: String?,
    val workedHours: Int?,
    val clockInSessionId: String?,
    val clockOutSessionId: String?
)