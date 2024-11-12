package com.example.yichen.yichen_kwokwing_comp304sec001_lab03.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.androidx.compose.koinViewModel
import androidx.navigation.NavController
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.model.Weather
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.navigation.Screen
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.viewmodel.WeatherViewModel
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.utils.Resource

@Composable
fun HomeScreen(navController: NavController) {
    val weatherViewModel: WeatherViewModel = koinViewModel()
    var location by remember { mutableStateOf("") }
    var clickFlag = false

    Box(Modifier.safeDrawingPadding()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Weather",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Get the latest weather updates",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Row(modifier = Modifier
                .fillMaxWidth(),
                //.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween) {
                TextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Enter location") },
                    modifier = Modifier
                        .fillMaxWidth(0.7f),
                        //.padding(end = 8.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    shape = RoundedCornerShape(10.dp)
                )
                Button(
                    onClick = { weatherViewModel.getWeatherForLocation(location);clickFlag=true },
                    modifier = Modifier
                        //.fillMaxWidth(),
                        .padding(end = 10.dp),
                    shape = RoundedCornerShape(99.dp)
                ) {
                    Icon(Icons.Default.Search, contentDescription = "Get Weather")
                    //Spacer(modifier = Modifier.width(4.dp))
                    //Text("Get Weather")
                }
            }

            when (val weatherState = weatherViewModel.weatherState.collectAsState().value) {
                is Resource.Success -> {
                    val weather = weatherState.data
                    weather?.let {
                        WeatherCard(it) {
                            navController.navigate(
                                Screen.WeatherDetail.route.replace(
                                    "{location}",
                                    it.name
                                )
                            )
                        }
                    }
                }

                is Resource.Error -> {
                    Text("Error: ${weatherState.message}")
                }

                is Resource.Loading -> {
                    if (clickFlag)
                        CircularProgressIndicator()
                }
            }
            FavoriteLocationsScreen(navController)
        }

    }
}

@Composable
fun WeatherCard(weather: Weather, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .clickable(onClick = onClick),
            //.background(color = Color.Red),
        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Row(modifier = Modifier.padding(10.dp)) {
            Column {
                Text("Location: ${weather.name}")
            }
            Column(modifier = Modifier.padding(6.dp)) {
                SwitchWithIcon(false)
            }
        }
    }
}

@Composable
fun WeatherCard2(weather: Weather, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .clickable(onClick = onClick)
    ) {
        Row(modifier = Modifier.padding(10.dp)) {
            Column {
                Text("Location: ${weather.name}")
                Text("Temperature: ${weather.temp_c}°C")
                //Text("Favorite Location: ${weather.isFavorite}")
            }
            Column(modifier = Modifier.padding(6.dp)) {
                SwitchWithIcon()
            }
        }


    }
}

@Composable
fun SwitchWithIcon(status: Boolean =true) {
    var checked by remember { mutableStateOf(status) }

    Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(text = "Save", modifier = Modifier.padding(end = 8.dp))
        Switch(
            checked = checked,
            onCheckedChange = {
                checked = it
            },
            thumbContent = if (checked) {
                {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                    )
                    
                }
            } else {
                null
            }
        )
    }
}