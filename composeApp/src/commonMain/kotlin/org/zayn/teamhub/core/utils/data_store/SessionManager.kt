package org.zayn.teamhub.core.utils.data_store

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.zayn.teamhub.core.models.AppSettings
import org.zayn.teamhub.core.models.Company
import org.zayn.teamhub.core.models.User

class SessionManager(private val pref: ISharedPrefManager) : ISessionManager {
    private companion object {
        private const val USER = "user"
        private const val COMPANY = "company"
        private const val APP_SETTINGS = "app_settings"
    }

    override fun putUser(user: User) {
        pref.setString(USER, encodeToString(user))
    }

    override fun getUser(): User? {
        return decodeFromString(pref.getString(USER))
    }

    override fun putCompany(company: Company) {
        pref.setString(COMPANY, encodeToString(company))
    }

    override fun getCompany(): Company? {
        return decodeFromString(pref.getString(COMPANY))
    }

    override fun putAppSettings(appSettings: AppSettings) {
        pref.setString(APP_SETTINGS, encodeToString(appSettings))
    }

    override fun getAppSettings(): AppSettings? {
        return decodeFromString(pref.getString(APP_SETTINGS))
    }
}