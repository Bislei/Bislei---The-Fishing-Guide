package com.kashmir.bislei.viewmodel

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LocationViewModel(application: Application) : AndroidViewModel(application) {

    private val _currentLocation = MutableStateFlow<LatLng?>(null)
    val currentLocation = _currentLocation.asStateFlow()

    fun fetchCurrentLocation() {
        // Get the application context from the AndroidViewModel
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplication<Application>().applicationContext)

        try {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    location?.let {
                        _currentLocation.value = LatLng(it.latitude, it.longitude)
                    }
                }
        } catch (e: SecurityException) {
            // Handle case where permission is not granted
        }
    }
}
