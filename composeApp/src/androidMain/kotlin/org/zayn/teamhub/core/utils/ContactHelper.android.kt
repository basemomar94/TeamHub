package org.zayn.teamhub.core.utils

import android.content.Intent
import android.net.Uri

actual fun sendEmail(email: String) {
    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:$email")
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    AppContext.get().startActivity(emailIntent)
}

actual fun dialContact(number: String) {
    val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number")).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    AppContext.get().startActivity(callIntent)
}

actual fun sendWhatsapp(number: String) {
    val whatsappIntent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse("https://wa.me/$number")
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    AppContext.get().startActivity(whatsappIntent)
}