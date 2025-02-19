package org.zayn.teamhub.core.utils.data_store

import org.zayn.teamhub.core.models.Roles

interface ISessionManager {


    fun putUserId(id: String)
    fun getUserId(): String?

    fun putUserRole(role: String?)
    fun getUserRole(): Roles
}