package com.example.yichen.yichen_kwokwing_comp304sec001_lab03.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.model.Weather
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.navigation.Screen
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.viewmodel.WeatherViewModel
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.utils.Resource

@Composable
fun HomeScreen(navController: NavController, weatherViewModel: WeatherViewModel = viewModel()) {
    var location by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Enter location") }
        )
        Button(onClick = { weatherViewModel.getWeatherForLocation(location) }) {
            Text("Get Weather")
        }
        Button(onClick = { navController.navigate(Screen.FavoriteLocations.route) }) {
            Text("Favorite Locations")
        }
        when (val weatherState = weatherViewModel.weatherState.collectAsState().value) {
            is Resource.Success -> {
                val weather = weatherState.data
                weather?.let {
                    WeatherCard(it) {
                        navController.navigate(Screen.WeatherDetail.route.replace("{location}", it.location))
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

@Composable
fun WeatherCard(weather: Weather, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Location: ${weather.location}")
            Text("Temperature: ${weather.temperature}°C")
            Text("Description: ${weather.description}")
        }
    }
}