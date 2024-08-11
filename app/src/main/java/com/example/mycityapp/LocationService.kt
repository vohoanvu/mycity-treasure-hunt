package com.example.mycityapp

import android.annotation.SuppressLint
import android.os.Looper
import com.google.android.gms.location.*

/* Assignment 5 Demo
LocationService.kt
Vu Vo / vovu@oregonstate.edu
CS 492 / Oregon State University
*/

class LocationService(private val fusedLocationClient: FusedLocationProviderClient) {

    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    init {
        // Initialize the FusedLocationProviderClient without needing the Activity context
        createLocationRequest()
    }

    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000L)
            .setMinUpdateIntervalMillis(3000L)
            .setMinUpdateDistanceMeters(10f)
            .build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                val location = locationResult.lastLocation
                // Handle the location update
            }
        }
    }

    fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}