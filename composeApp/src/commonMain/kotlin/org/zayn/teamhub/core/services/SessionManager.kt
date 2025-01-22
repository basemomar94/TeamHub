package org.zayn.teamhub.core.services

class SessionManager(private val pref: SharedPref) : ISessionManager {
    override fun putUserId(id: String) {
        pref.setString(SessionKeys.USER_ID_KEY, id)
    }

    override fun getUserId(): String? {
        return pref.getString(SessionKeys.USER_ID_KEY)
    }
}