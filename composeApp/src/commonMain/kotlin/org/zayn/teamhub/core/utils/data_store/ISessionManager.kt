package org.zayn.teamhub.core.utils.data_store

import org.zayn.teamhub.core.models.Roles
import org.zayn.teamhub.core.models.User

interface ISessionManager {


    fun putUserId(id: String)
    fun getUserId(): String?

    fun putUserRole(role: String?)
    fun getUserRole(): Roles

    fun putUser(user: User)
    fun getUser(): User?
}