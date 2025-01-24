package org.zayn.teamhub.core.services

class SessionManager {
    fun putUserId(id: String) {
      //  pref.setString(SessionKeys.USER_ID_KEY, id)
    }

    fun getUserId(): String? {
    //    return pref.getString(SessionKeys.USER_ID_KEY)
        return ""
    }
}