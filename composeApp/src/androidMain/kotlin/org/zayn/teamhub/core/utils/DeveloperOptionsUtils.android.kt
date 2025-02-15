package org.zayn.teamhub.core.utils

import android.provider.Settings

actual fun isDeveloperOptionEnabled(): Boolean {
    return try {
        Settings.Global.getInt(
            AppContext.get().contentResolver,
            Settings.Global.DEVELOPMENT_SETTINGS_ENABLED,
            0
        ) == 1
    } catch (e: Exception) {
        false
    }
}