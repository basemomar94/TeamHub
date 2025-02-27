package org.zayn.teamhub.core.utils

import android.content.pm.PackageManager
import com.google.firebase.BuildConfig

actual fun getAppVersion(): String {
    return try {
        val packageInfo =
            AppContext.get().packageManager.getPackageInfo(AppContext.get().packageName, 0)
        packageInfo.versionName ?: "Unknown"
    } catch (e: PackageManager.NameNotFoundException) {
        "Unknown"
    }

}

actual fun getBuildVariant(): String {
    return BuildConfig.BUILD_TYPE
}