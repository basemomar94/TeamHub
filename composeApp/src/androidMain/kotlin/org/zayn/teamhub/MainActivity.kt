package org.zayn.teamhub

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import org.zayn.teamhub.core.navigation.App
import org.zayn.teamhub.core.utils.Logger.Companion.createLogger

class MainActivity : ComponentActivity() {
    private val logger = this.createLogger()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        requestLocationPermission()

        setContent {
            App()
        }
    }

    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            logger.e("location permission isn't granted requesting it ")
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            logger.i("location is already granted")
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {

            } else {
                Toast.makeText(this, "Please allow location", Toast.LENGTH_SHORT).show()
            }
        }
}
