package org.zayn.teamhub.core.utils.data_store

interface ISharedPrefManager {
    fun getString(
        key: String,
        defaultValue: String,

        ): String?

    fun getString(key: String, ): String?
    fun setString(key: String, value: String)
    fun getBoolean(
        key: String,
        defaultValue: Boolean,

        ): Boolean

    fun setBoolean(key: String, value: Boolean, )

    fun getLong(
        key: String,
        defaultValue: Long,

        ): Long?

    fun setLong(key: String, value: Long, )

    fun containsKey(key: String, ): Boolean
    fun clearPref(key: String, )
    fun clear()
}