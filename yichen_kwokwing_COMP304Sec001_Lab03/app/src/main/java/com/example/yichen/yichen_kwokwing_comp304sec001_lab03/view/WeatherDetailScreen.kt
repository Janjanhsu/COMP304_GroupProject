package com.example.yichen.yichen_kwokwing_comp304sec001_lab03.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.viewmodel.WeatherViewModel
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.utils.Resource
import org.koin.androidx.compose.koinViewModel

@Composable
fun WeatherDetailScreen(location: String, navController: NavController) {
    val weatherViewModel: WeatherViewModel = koinViewModel()
    LaunchedEffect(location) {
        weatherViewModel.getWeatherForLocation(location)
    }

    Box(Modifier.safeDrawingPadding()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Button(onClick = { navController.popBackStack() }) {
                Text("Back")
            }

            when (val weatherState = weatherViewModel.weatherState.collectAsState().value) {
                is Resource.Success -> {
                    val weather = weatherState.data
                    weather?.let {
                        Text("Location: ${it.name}")
                        Text("Temperature: ${it.temp_c}°C")
                        Text("Favorite Location: ${it.isFavorite}")
                        Button(onClick = { weatherViewModel.addFavoriteLocation(it) }) {
                            Text("Add to Favorites")
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
