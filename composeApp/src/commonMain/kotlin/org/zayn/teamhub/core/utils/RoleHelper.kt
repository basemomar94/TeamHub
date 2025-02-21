package org.zayn.teamhub.core.utils

import org.zayn.teamhub.core.models.Roles
import org.zayn.teamhub.core.models.User

fun User?.isAdmin(): Boolean {
    val roleTxt = this?.role
    return Roles.ADMIN == enumValueOrNullOf<Roles>(roleTxt)
}