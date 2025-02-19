package org.zayn.teamhub.core.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String? = "",
    val firstName: String? = "",
    val lastName: String? = "",
    val companyId: String? = "",
    val email: String? = "",
    val password: String? = "",
    val createdAt: Long? = 0L,
    val lastUpdate: Long? = 0L,
    val currentStatus: String? = null,
    val role: String? = null,
    val phoneNumber: String? = null,
    val profileImage: String? = null
)