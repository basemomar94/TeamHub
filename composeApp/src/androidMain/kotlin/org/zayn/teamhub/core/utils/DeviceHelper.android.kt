package org.zayn.teamhub.core.utils

import android.os.Build

actual fun getFullDeviceName(): String {
    val model = Build.MODEL
    val manufacturer = Build.MANUFACTURER
    return "$manufacturer $model"
}