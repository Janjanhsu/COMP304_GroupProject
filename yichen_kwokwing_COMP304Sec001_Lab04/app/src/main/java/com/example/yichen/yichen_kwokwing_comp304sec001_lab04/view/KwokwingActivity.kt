package com.example.yichen.yichen_kwokwing_comp304sec001_lab04.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Color
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Route
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.ui.theme.MapStyle
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.viewmodel.LocationViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class, MapsComposeExperimentalApi::class)
@Composable
fun KwokwingActivity(attraction: String, navController: NavController) {
    // To get user location and check to get the permission
    val context = LocalContext.current
    val activity = LocalContext.current as? Activity
    val permissionState = remember { mutableStateOf(false) }
    // Create MapStyleOptions from JSON string
    val mapStyleOptions = remember { MapStyleOptions(MapStyle.json) }
    val locationViewModel: LocationViewModel = koinViewModel()
    val coroutineScope = rememberCoroutineScope()
    // default location of Centennial College
    val defaultLocation = LatLng(43.7848528,-79.2308108)
    // User interaction marker
    var markerPosition by remember { mutableStateOf(defaultLocation) }
    var userLocation by remember { mutableStateOf(LatLng(0.0,0.0)) }
    var hasLocationPermission by remember { mutableStateOf(false) }
    val attractionLocation by locationViewModel.attractionLocation.collectAsState()
    var polylinePoints by remember { mutableStateOf<List<LatLng>>(emptyList()) }

    LaunchedEffect(attraction) {
        locationViewModel.updateAttractionLocation(attraction)
    }
    LaunchedEffect(Unit) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request permissions
            activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    1001 // Request code
                )
            }
        } else {
            // Permissions granted
            permissionState.value = true
        }
        if (permissionState.value) {
            Log.i("myApp", "Location permissions granted.")
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                hasLocationPermission = true
                val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        userLocation = LatLng(location.latitude, location.longitude)
                        Log.i("myApp", "Updated userLocation: $userLocation")
                    } else {
                        Log.i("myApp", "Last location is null, requesting fresh location...")
                    }
                }
            }
        } else {
            Log.i("myApp", "Waiting for location permissions.")
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(attraction) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier
            .padding(padding)
            .fillMaxSize()) {
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(attractionLocation, 15f)
            }
            //Log.i("myApp", "attractionLocation"+attractionLocation)

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                onMapClick = { latLng ->
                    markerPosition = latLng
                },
                cameraPositionState = cameraPositionState,
                properties = MapProperties(isMyLocationEnabled = hasLocationPermission,
                    mapStyleOptions = mapStyleOptions)
            ) {
                if(markerPosition != defaultLocation){
                    Marker(
                        state = remember {
                            MarkerState(position = markerPosition)
                        },
                        title = "New Marker",
                        snippet = "This is a dynamically added marker."
                    )
                }
                Marker(
                    state = remember {
                        MarkerState(position = attractionLocation)
                    },
                    title = attraction,
                    snippet = "Attraction Location"
                )
                Marker(
                    state = remember {
                        MarkerState(position = defaultLocation)
                    },
                    title = "Centennial College",
                    snippet = "Default location"
                )

                if (hasLocationPermission && userLocation.latitude != 0.0 && userLocation.longitude != 0.0) {
                    Marker(
                        state = remember {
                            MarkerState(position = userLocation)
                        },
                        title = "Your Location",
                        snippet = "You are here"
                    )
                }
                DrawGeofencing()
                if (polylinePoints.isNotEmpty()) {
                    DrawPolyline(polylinePoints = polylinePoints)
                }
                //DrawGeofencing()

                coroutineScope.launch {
                    cameraPositionState.animate(
                        update = CameraUpdateFactory.newLatLngZoom(
                            attractionLocation,
                            15f
                        ),
                        durationMs = 1000
                    )
                }
            }

            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        cameraPositionState.animate(
                            update = CameraUpdateFactory.newLatLngZoom(
                                attractionLocation,
                                15f
                            ),
                            durationMs = 1000
                        )
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .offset(x = (-45).dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Center on attraction"
                )
            }

            FloatingActionButton(
                onClick = {
                    polylinePoints = listOf(defaultLocation, attractionLocation)
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Route,
                    contentDescription = "Route between Centennial and attraction"
                )

            }

            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        cameraPositionState.animate(
                            update = CameraUpdateFactory.newLatLngZoom(
                                defaultLocation,
                                15f
                            ),
                            durationMs = 1000
                        )
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
                    .offset(x = (45).dp)
            ) {
                Icon(
                    imageVector = Icons.Default.School,
                    contentDescription = "Center on default location"
                )
            }
        }
    }
}

@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun DrawPolyline(polylinePoints: List<LatLng>) {
    MapEffect(polylinePoints) { googleMap ->
        // Clear all existing polylines on the map
        //googleMap.clear()
        // Ensure the polylinePoints list is not empty
        if (polylinePoints.isNotEmpty()) {
            // Create the PolylineOptions
            val polylineOptions = PolylineOptions()
                .addAll(polylinePoints)  // Add points to the polyline
                .color(android.graphics.Color.BLUE)  // Set polyline color
                .width(10f)  // Set polyline width

            // Add polyline to the map
            googleMap.addPolyline(polylineOptions)
        }
    }
}

@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun DrawGeofencing() {
    MapEffect() { googleMap ->
        /* googleMap.addCircle(
            CircleOptions()
                .center(latlng)
                .radius(10000.0)
                .strokeColor(Color.RED)
                //.fillColor(Color.parseColor("#FFFFC5"))
        )*/
        googleMap.addPolygon(
            PolygonOptions()
            .add(
                LatLng(43.159947, -79.786418),
                LatLng(43.231905, -79.977329),
                LatLng(43.682605, -79.875634),
                LatLng(43.950151, -79.512849),
                LatLng(44.171780,-79.543531),
                LatLng(44.384156,-79.270344),
                LatLng(43.906633, -79.196897),
                LatLng(43.744147, -79.087032)
            )
            .strokeColor(Color.RED)
        )
        //polygon1.setTag("alpha");
        //private static final int COLOR_LIGHT_ORANGE_ARGB = 0xffF9A825;
    }
}