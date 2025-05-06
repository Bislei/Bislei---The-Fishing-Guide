package com.kashmir.bislei.screens

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.permissions.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.kashmir.bislei.viewmodel.FishingSpotsViewModel
import com.kashmir.bislei.viewmodel.LocationViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.kashmir.bislei.model.FishingSpot
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    fishingSpotsViewModel: FishingSpotsViewModel = viewModel(),
    locationViewModel: LocationViewModel = viewModel()
) {
    val permissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(34.1106, 74.8683), 12f
        )
    }

    val fishingSpots by fishingSpotsViewModel.fishingSpots.collectAsState()
    val currentLocation by locationViewModel.currentLocation.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    var selectedSpot by remember { mutableStateOf<FishingSpot?>(null) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(Unit) {
        permissionsState.launchMultiplePermissionRequest()
        if (permissionsState.allPermissionsGranted) {
            locationViewModel.fetchCurrentLocation()
        }
    }

    LaunchedEffect(fishingSpots) {
        if (fishingSpots.isNotEmpty()) {
            val firstSpot = fishingSpots.first()
            cameraPositionState.position = CameraPosition.fromLatLngZoom(
                LatLng(firstSpot.latitude, firstSpot.longitude), 12f
            )
        } else {
            currentLocation?.let {
                cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 12f)
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            if (permissionsState.allPermissionsGranted) {
                FloatingActionButton(
                    onClick = {
                        currentLocation?.let {
                            Log.d("FAB_DEBUG", "FAB clicked, moving to current location")
                            coroutineScope.launch {
                                cameraPositionState.animate(
                                    update = CameraUpdateFactory.newLatLngZoom(it, 15f)
                                )
                            }
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White,
                    modifier = Modifier.padding(bottom = 170.dp)
                ) {
                    Icon(Icons.Default.MyLocation, contentDescription = "My Location")
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (permissionsState.allPermissionsGranted) {
                GoogleMap(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 80.dp),
                    cameraPositionState = cameraPositionState
                ) {
                    fishingSpots.forEach { spot ->
                        Marker(
                            state = MarkerState(position = LatLng(spot.latitude, spot.longitude)),
                            title = spot.name,
                            snippet = spot.description,
                            onClick = {
                                selectedSpot = spot
                                coroutineScope.launch {
                                    bottomSheetState.show()
                                }
                                true
                            }
                        )
                    }
                }
            } else {
                PermissionDeniedUI(permissionsState)
            }
        }
    }

    selectedSpot?.let { spot ->
        ModalBottomSheet(
            onDismissRequest = { selectedSpot = null },
            sheetState = bottomSheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Location Name (add it at the top)
                Text(
                    text = spot.name,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Location Description
                Text(
                    text = spot.description,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Location Name:",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold) // Bold heading
                )
                Spacer(modifier = Modifier.height(8.dp))
                
                // Location Name
                Text(
                    text = spot.locationName,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Display Fish Types
                if (spot.fishTypes.isNotEmpty()) {
                    Text(
                        text = "Fish Types:",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold) // Bold heading
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    spot.fishTypes.forEach { fishType ->
                        Text(text = "• $fishType", style = MaterialTheme.typography.bodyMedium)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Display Nearby Locations
                if (spot.bestFishingLocationsNearby.isNotEmpty()) {
                    Text(
                        text = "Nearby Locations:",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold) // Bold heading
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    spot.bestFishingLocationsNearby.forEach { location ->
                        Text(text = "• $location", style = MaterialTheme.typography.bodyMedium)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Display images in a LazyRow
                if (spot.imageUrls.isNotEmpty()) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(spot.imageUrls) { imageUrl ->
                            Image(
                                painter = rememberAsyncImagePainter(imageUrl),
                                contentDescription = null,
                                modifier = Modifier
                                    .width(300.dp)
                                    .fillMaxHeight(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Directions FAB IconButton
                val context = LocalContext.current
                FloatingActionButton(
                    onClick = {
                        // Open Google Maps with directions to the selected fishing spot
                        val gmmIntentUri = Uri.parse("google.navigation:q=${spot.latitude},${spot.longitude}")
                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                        mapIntent.setPackage("com.google.android.apps.maps")
                        context.startActivity(mapIntent) // Using LocalContext to get the context
                    },
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(8.dp), // Optional: adds some padding
                    containerColor = MaterialTheme.colorScheme.primary, // Set the background color for the FAB
                    contentColor = Color.White // Set the color of the icon inside the FAB
                ) {
                    Icon(
                        imageVector = Icons.Default.Directions, // Directions icon
                        contentDescription = "Get Directions"
                    )
                }
            }
        }
    }

}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionDeniedUI(permissionsState: MultiplePermissionsState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Location permission is required to use the map", color = Color.Red)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { permissionsState.launchMultiplePermissionRequest() }) {
            Text("Grant Permission")
        }
    }
}
