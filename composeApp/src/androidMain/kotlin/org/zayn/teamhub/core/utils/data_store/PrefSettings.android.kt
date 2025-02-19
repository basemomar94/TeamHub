package org.zayn.teamhub.core.utils.data_store

import android.preference.PreferenceManager
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import org.zayn.teamhub.core.utils.AppContext

actual fun getSettings(): Settings = SharedPreferencesSettings(
    PreferenceManager.getDefaultSharedPreferences(AppContext.get())
)