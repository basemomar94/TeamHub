package org.zayn.teamhub.core.utils.data_store

class SharedPrefManager : ISharedPrefManager {
    private val pref = getSettings()

    override fun getString(key: String, defaultValue: String): String {
        return pref.getString(key, defaultValue)
    }

    override fun getString(key: String): String? {
        return pref.getStringOrNull(key)
    }

    override fun setString(key: String, value: String) {
        pref.putString(key, value)
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return pref.getBoolean(key, defaultValue)
    }

    override fun setBoolean(key: String, value: Boolean) {
        pref.putBoolean(key, value)
    }

    override fun getLong(key: String, defaultValue: Long): Long? {
       return pref.getLong(key, defaultValue)
    }

    override fun setLong(key: String, value: Long) {
        pref.putLong(key, value)
    }

    override fun containsKey(key: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun clearPref(key: String) {
        TODO("Not yet implemented")
    }

    override fun clear() {
        TODO("Not yet implemented")
    }
}