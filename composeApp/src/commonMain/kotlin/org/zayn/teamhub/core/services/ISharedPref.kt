package org.zayn.teamhub.core.services

interface ISharedPref {

    fun getString(
        key: String,
        defaultValue: String? = null,

        ): String?

    fun setString(key: String, value: String)
    fun getBoolean(
        key: String,
        defaultValue: Boolean,

        ): Boolean

    fun setBoolean(key: String, value: Boolean)

    fun getLong(
        key: String,
        defaultValue: Long? = null,

        ): Long?

    fun setLong(key: String, value: Long)

    fun containsKey(key: String): Boolean
    fun clearPref(key: String)
    fun clear()
}