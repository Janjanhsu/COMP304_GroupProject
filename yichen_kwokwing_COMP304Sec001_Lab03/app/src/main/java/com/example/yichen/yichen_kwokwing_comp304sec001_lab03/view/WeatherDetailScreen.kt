package com.example.yichen.yichen_kwokwing_comp304sec001_lab03.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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

@Composable
fun WeatherDetailScreen(location: String, navController: NavController, weatherViewModel: WeatherViewModel = viewModel()) {
    LaunchedEffect(location) {
        weatherViewModel.getWeatherForLocation(location)
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }

        when (val weatherState = weatherViewModel.weatherState.collectAsState().value) {
            is Resource.Success -> {
                val weather = weatherState.data
                weather?.let {
                    Text("Location: ${it.location}")
                    Text("Temperature: ${it.temperature}°C")
                    Text("Description: ${it.description}")
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
