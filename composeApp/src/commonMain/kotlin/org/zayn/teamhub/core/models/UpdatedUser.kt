package org.zayn.teamhub.core.models

data class UpdatedUser(
    val userId: String?,
    val firstName: String?,
    val lastName: String?,
    val phoneNumber: String?,
    val email: String?,
    val deviceName: String?
)
