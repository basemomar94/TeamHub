package org.zayn.teamhub.core.utils

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri

actual fun openUrl(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
        flags = FLAG_ACTIVITY_NEW_TASK
    }
    AppContext.get().startActivity(intent)
}