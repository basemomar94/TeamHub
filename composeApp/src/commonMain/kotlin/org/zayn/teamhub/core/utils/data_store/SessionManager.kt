package org.zayn.teamhub.core.utils.data_store

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.zayn.teamhub.core.models.Company
import org.zayn.teamhub.core.models.User

class SessionManager(private val pref: ISharedPrefManager) : ISessionManager {
    private companion object {
        private const val USER = "user"
        private const val COMPANY = "company"
    }

    override fun putUser(user: User) {
        val jsonString = Json.encodeToString(user)
        pref.setString(USER, jsonString)
    }

    override fun getUser(): User? {
        val jsonString = pref.getString(USER)
        return jsonString?.let { Json.decodeFromString<User>(it) }
    }

    override fun putCompany(company: Company) {
        val jsonString = Json.encodeToString(company)
        pref.setString(COMPANY, jsonString)
    }

    override fun getCompany(): Company? {
        val jsonString = pref.getString(COMPANY)
        return jsonString?.let { Json.decodeFromString<Company>(it) }
    }
}