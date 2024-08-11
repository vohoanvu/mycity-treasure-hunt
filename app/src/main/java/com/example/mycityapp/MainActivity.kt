package com.example.mycityapp

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.example.mycityapp.ui.theme.MyCityAppTheme
import android.Manifest
import com.google.android.gms.location.LocationServices

/* Assignment 5 Demo
MainActivity.kt
Vu Vo / vovu@oregonstate.edu
CS 492 / Oregon State University
*/

class MainActivity : ComponentActivity() {
    private lateinit var locationService: LocationService

    // Register the permissions callback, which handles the user's response to the system permissions dialog.
    private val requestPermissionLauncher =
        this.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false)
                && permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false)) {
                locationService.startLocationUpdates()
            } else {
                // Handle permission denied case
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyCityAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TreasureHuntGameApp()

                    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
                    locationService = LocationService(fusedLocationClient)

                    // Request permissions and start location updates
                    checkPermissionsAndRequestLocationUpdates()
                }
            }
        }
    }


    fun checkPermissionsAndRequestLocationUpdates() {
        when {
            ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED -> {
                    locationService.startLocationUpdates()
            }
            else -> {
                requestPermissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION))
            }
        }
    }

    override fun onPause() {
        super.onPause()
        locationService.stopLocationUpdates()
    }
}



@Preview(showBackground = true)
@Composable
fun MyAppPreview() {
    MyCityAppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            TreasureHuntGameApp()
        }
    }
}