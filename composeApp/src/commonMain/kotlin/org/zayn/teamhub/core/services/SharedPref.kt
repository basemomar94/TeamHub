package org.zayn.teamhub.core.services

import com.russhwolf.settings.Settings
import com.russhwolf.settings.contains

class SharedPref(private val settings: Settings) : ISharedPref {

    override fun getString(key: String, defaultValue: String?): String? {
        return settings.getStringOrNull(key) ?: defaultValue
    }

    override fun setString(key: String, value: String) {
        settings.putString(key, value)
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return settings.getBoolean(key, defaultValue)
    }

    override fun setBoolean(key: String, value: Boolean) {
        settings.putBoolean(key, value)
    }

    override fun getLong(key: String, defaultValue: Long?): Long? {
        return settings.getLongOrNull(key) ?: defaultValue
    }

    override fun setLong(key: String, value: Long) {
        settings.putLong(key, value)
    }

    override fun containsKey(key: String): Boolean {
        return settings.contains(key)
    }

    override fun clearPref(key: String) {
        settings.remove(key)
    }

    override fun clear() {
        settings.clear()
    }
}