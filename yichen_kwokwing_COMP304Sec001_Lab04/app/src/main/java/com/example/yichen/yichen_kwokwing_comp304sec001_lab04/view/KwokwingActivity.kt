package com.example.yichen.yichen_kwokwing_comp304sec001_lab04.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdsClick
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
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.data.DirectionAPI
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.ui.theme.MapStyle
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.viewmodel.LocationViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
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
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
    val defaultLocation = LatLng(43.7848528, -79.2308108)
    // User interaction marker
    var markerPosition by remember { mutableStateOf(defaultLocation) }
    var userLocation by remember { mutableStateOf(LatLng(0.0, 0.0)) }
    var hasLocationPermission by remember { mutableStateOf(false) }
    val attractionLocation by locationViewModel.attractionLocation.collectAsState()
    var polylinePoints by remember { mutableStateOf<List<LatLng>>(emptyList()) }
    var isTrack = false

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
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
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
                properties = MapProperties(
                    isMyLocationEnabled = hasLocationPermission,
                    mapStyleOptions = mapStyleOptions
                )
            ) {

                Marker(
                    state = remember(markerPosition) {
                        MarkerState(position = markerPosition)
                    },
                    title = "New Marker",
                    snippet = "This is a dynamically added marker."
                )

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
                    coroutineScope.launch {
                        polylinePoints =
                            getDirection("${attractionLocation.latitude},${attractionLocation.longitude}", "${userLocation.latitude},${userLocation.longitude}")
                    }
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

            FloatingActionButton(
                onClick = {
                    if (isTrack){
                        isTrack=false
                        Toast.makeText(context, "Track stopped", Toast.LENGTH_SHORT).show()
                    } else {
                        isTrack=true
                        Toast.makeText(context, "Track started", Toast.LENGTH_SHORT).show()
                    }

                    val fusedLocationClient =
                        LocationServices.getFusedLocationProviderClient(context)
                    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                        if (location != null) {
                            userLocation = LatLng(location.latitude, location.longitude)
                            Log.i("myApp", "Updated userLocation: $userLocation")
                        } else {
                            Log.i(
                                "myApp",
                                "Last location is null, requesting fresh location..."
                            )
                        }
                    }
                    val locationCallback = object : LocationCallback() {
                        override fun onLocationResult(locationResult: LocationResult) {
                            locationResult.locations.forEach { location ->
                                val latLng = LatLng(location.latitude, location.longitude)
                                if (isTrack) {
                                    coroutineScope.launch {
                                        cameraPositionState.animate(
                                            update = CameraUpdateFactory.newLatLngZoom(
                                                latLng,
                                                15f
                                            ),
                                            durationMs = 1000
                                        )
                                    }
                                }
                            }
                        }
                    }
                    startLocationUpdates(
                        navController.context,
                        locationCallback,
                        fusedLocationClient
                    )

                },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
                    //.offset(x = (45).dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AdsClick,
                    contentDescription = "Track"
                )

            }
        }
    }
}


@Composable
fun DrawPolyline(polylinePoints: List<LatLng>) {
        if (polylinePoints.isNotEmpty()) {
            Polyline(
                points = polylinePoints,
                width = 10f,
                geodesic = true
            )
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
                    LatLng(44.171780, -79.543531),
                    LatLng(44.384156, -79.270344),
                    LatLng(43.906633, -79.196897),
                    LatLng(43.744147, -79.087032)
                )
                .strokeColor(Color.RED)
        )
    }
}

private fun startLocationUpdates(context: Context, locationCallback: LocationCallback, fusedLocationClient: FusedLocationProviderClient) {
    val locationRequest = LocationRequest.create().apply {
        interval = 10000
        fastestInterval = 5000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }
    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
        PackageManager.PERMISSION_GRANTED &&
        ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) !=
        PackageManager.PERMISSION_GRANTED) {
        return
    }
    fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback,
        Looper.getMainLooper())
}

suspend fun getDirection(destination: String, origin: String): List<LatLng>{
    val directionAPI = DirectionAPI()
    var routePoints: List<LatLng> = listOf(LatLng(0.0,0.0))
    try {
        val response = directionAPI.getDirection(origin, destination)
        if (response.isSuccessful) {
            Log.e("DirectionAPI", response.body()?.toString() ?: "Empty response")
            val directionsResponse = response.body()
            val polyline = directionsResponse?.routes?.firstOrNull()?.overviewPolyline?.points
            if (polyline != null) {
                routePoints = decodePolyline(polyline)
            } else {
                Log.e("DirectionAPI", "No polyline found in response.")
            }
        } else {
            Log.e("DirectionAPI", "Error: ${response.errorBody()?.string()}")
        }
    } catch (e: Exception) {
        Log.e("DirectionAPI", "Exception: ${e.message}")
    }
    return routePoints
}

fun decodePolyline(encoded: String): List<LatLng> {
    val polylinePoints = mutableListOf<LatLng>()
    var index = 0
    val length = encoded.length
    var lat = 0
    var lng = 0

    while (index < length) {
        var shift = 0
        var result = 0
        var byte: Int
        do {
            byte = encoded[index++].code - 63
            result = result or (byte and 0x1f shl shift)
            shift += 5
        } while (byte >= 0x20)
        val deltaLat = if (result and 1 != 0) (result shr 1).inv() else (result shr 1)
        lat += deltaLat

        shift = 0
        result = 0
        do {
            byte = encoded[index++].code - 63
            result = result or (byte and 0x1f shl shift)
            shift += 5
        } while (byte >= 0x20)
        val deltaLng = if (result and 1 != 0) (result shr 1).inv() else (result shr 1)
        lng += deltaLng

        val decodedLat = lat / 1E5
        val decodedLng = lng / 1E5
        polylinePoints.add(LatLng(decodedLat, decodedLng))
    }

    return polylinePoints
}
