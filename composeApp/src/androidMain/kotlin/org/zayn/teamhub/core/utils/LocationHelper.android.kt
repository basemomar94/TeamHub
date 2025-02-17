package org.zayn.teamhub.core.utils

import android.content.Intent
import android.location.Location
import android.net.Uri
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import org.zayn.teamhub.core.utils.Logger.Companion.createLogger
import kotlin.coroutines.resume

actual suspend fun getCurrentLocation(): Pair<Double, Double>? {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(AppContext.get())

    return suspendCancellableCoroutine { continuation ->
        if (androidx.core.content.ContextCompat.checkSelfPermission(
                AppContext.get(), android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            continuation.resume(null)
            return@suspendCancellableCoroutine
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    continuation.resume(Pair(it.latitude, it.longitude))
                } ?: continuation.resume(null)
            }
            .addOnFailureListener {
                continuation.resume(null)
            }
    }
}



actual fun openMap(latitude: Double, longitude: Double) {
    val logger = Logger.createLogger("openMap")
    try {
        logger.d("open map with $latitude $longitude")

        val gmmIntentUri = Uri.parse("geo:$latitude,$longitude")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // ✅ Required for non-Activity contexts
        }

        val context = AppContext.get()
        if (mapIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(mapIntent)
        } else {
            // Open in a web browser as a fallback
            val webUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=$latitude,$longitude")
            val webIntent = Intent(Intent.ACTION_VIEW, webUri).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(webIntent)
        }
    } catch (e: Exception) {
        logger.e("open map error ${e.message}")
    }
}

