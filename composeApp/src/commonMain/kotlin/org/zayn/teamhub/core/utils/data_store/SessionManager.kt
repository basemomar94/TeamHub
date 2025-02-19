package org.zayn.teamhub.core.utils.data_store

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.zayn.teamhub.core.models.Roles
import org.zayn.teamhub.core.models.User

class SessionManager(private val pref: ISharedPrefManager) : ISessionManager {
    private companion object {
        private const val USER_ID = "user_Id"
        private const val USER_ROLE = "user_role"
        private const val USER = "user"
    }

    override fun putUserId(id: String) {
        pref.setString(USER_ID, id)
    }

    override fun getUserId(): String? {
        return pref.getString(USER_ID)
    }

    override fun putUserRole(role: String?) {
        pref.setString(USER_ROLE, role ?: Roles.USER.name)
    }

    override fun getUserRole(): Roles {
        val roleTxt = pref.getString(USER_ROLE)
        return roleTxt?.let { Roles.valueOf(it) } ?: Roles.USER
    }

    override fun putUser(user: User) {
        val jsonString = Json.encodeToString(user)
        pref.setString(USER, jsonString)
    }

    override fun getUser(): User? {
        val jsonString = pref.getString(USER)
        return jsonString?.let { Json.decodeFromString<User>(it) }
    }
}