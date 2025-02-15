package org.zayn.teamhub.core.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.Build
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.pm.PackageManager

actual fun getConnectedWifiMacAddress(): String? {
    val context = AppContext.get().applicationContext
    val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as? WifiManager
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED
    ) {
        return "Permission Denied"
    }
    val network = connectivityManager?.activeNetwork
    val capabilities = connectivityManager?.getNetworkCapabilities(network)
    val isWifiConnected = capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ?: false

    if (!isWifiConnected) {
        return null
    }

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        (capabilities?.transportInfo as? android.net.wifi.WifiInfo)?.rssi.toString()
    } else {
        wifiManager?.connectionInfo?.bssid
    }
}
