package org.zayn.teamhub.core.utils.data_store

import org.zayn.teamhub.core.models.Roles

class SessionManager(private val pref: ISharedPrefManager) : ISessionManager {
    private companion object {
        private const val USER_ID = "user_Id"
        private const val USER_ROLE = "user_role"
    }

    override fun putUserId(id: String) {
        pref.setString(USER_ID, id)
    }

    override fun getUserId(): String? {
        return pref.getString(USER_ID)
    }

    override fun putUserRole(roles: Roles) {
        pref.setString(USER_ROLE, roles.name)
    }

    override fun getUserRole(): Roles {
        val roleTxt = pref.getString(USER_ROLE)
        return roleTxt?.let { Roles.valueOf(it) } ?: Roles.USER
    }
}