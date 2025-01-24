package org.zayn.teamhub.core.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String? = "",
    val firstName: String? = "",
    val lastName: String? = "",
    val isAdmin: Boolean? = false,
    val companyId: String? = "",
    val email: String? = ""
)