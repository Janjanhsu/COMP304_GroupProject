package com.example.yichen.yichen_kwokwing_comp304sec001_lab03.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.viewmodel.WeatherViewModel
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.utils.Resource
import org.koin.androidx.compose.koinViewModel
import coil.compose.AsyncImage

@Composable
fun WeatherDetailScreen(location: String, isFavorite: Boolean, navController: NavController) {
    val weatherViewModel: WeatherViewModel = koinViewModel()
    val weatherState = weatherViewModel.weatherState.collectAsState().value

    // Only fetch weather data if it's not already fetched
    LaunchedEffect(location) {
        if (weatherState !is Resource.Success || weatherState.data?.name != location) {
            weatherViewModel.getWeatherForLocation(location, isFavorite)
        }
    }
    Box(Modifier.safeDrawingPadding()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Button(onClick = { navController.popBackStack() }) {
                Text("Back")
            }
            when (weatherState) {
                is Resource.Success -> {
                    val weather = weatherState.data
                    weather?.let {
                        // Display location details
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .background(MaterialTheme.colorScheme.secondaryContainer)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                        ) {
                            Text(
                                text = "Location: ${it.name}",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "Region: ${it.region}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Country: ${it.country}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Latitude: ${it.lat}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Longitude: ${it.lon}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Timezone: ${it.tz_id}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Local Time: ${it.localtime}",
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Display current weather details
                            Text(
                                text = "Temperature: ${it.temp_c}°C",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.secondary
                            )
                            Text(
                                text = "Feels Like: ${it.feelslike_c}°C",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Humidity: ${it.humidity}%",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Cloud Cover: ${it.cloud}%",
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Display weather condition
                            WeatherIcon(iconUrl = it.condition.icon)

                            // testing
                            Text(text = "favorite location ${it.isFavorite}")
                        }

                    }
                }
                is Resource.Error -> {
                    Text("Error: ${weatherState.message}")
                }
                is Resource.Loading -> {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
@Composable
fun WeatherIcon(iconUrl: String) {
    AsyncImage(
        model = "https:$iconUrl",
        contentDescription = "Weather Icon",
        modifier = Modifier.size(64.dp)
    )
}
