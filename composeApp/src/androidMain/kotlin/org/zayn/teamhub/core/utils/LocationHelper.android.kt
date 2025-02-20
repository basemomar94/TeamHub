package org.zayn.teamhub.core.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import org.zayn.teamhub.core.utils.Logger.Companion.createLogger
import kotlin.coroutines.resume

actual suspend fun getCurrentLocation(): org.zayn.teamhub.core.models.Location? {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(AppContext.get())

    return suspendCancellableCoroutine { continuation ->
        if (ContextCompat.checkSelfPermission(
                AppContext.get(), Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            continuation.resume(null)
            return@suspendCancellableCoroutine
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    continuation.resume(org.zayn.teamhub.core.models.Location(it.latitude, it.longitude))
                } ?: continuation.resume(org.zayn.teamhub.core.models.Location())
            }
            .addOnFailureListener {
                continuation.resume(null)
            }
    }
}


actual fun openMap(latitude: Double?, longitude: Double?) {
    if (latitude == null || longitude == null) return
    val logger = Logger.createLogger("openMap")
    try {
        logger.d("open map with $latitude $longitude")

        val gmmIntentUri = Uri.parse("geo:$latitude,$longitude")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        val context = AppContext.get()
        if (mapIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(mapIntent)
        } else {
            val webUri =
                Uri.parse("https://www.google.com/maps/search/?api=1&query=$latitude,$longitude")
            val webIntent = Intent(Intent.ACTION_VIEW, webUri).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(webIntent)
        }
    } catch (e: Exception) {
        logger.e("open map error ${e.message}")
    }
}

actual fun isGpsAvailable(): Boolean {
    val context: Context = AppContext.get()
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)


}

actual fun isLocationAllowed(): Boolean {
    val context: Context = AppContext.get()
    return ContextCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

}

