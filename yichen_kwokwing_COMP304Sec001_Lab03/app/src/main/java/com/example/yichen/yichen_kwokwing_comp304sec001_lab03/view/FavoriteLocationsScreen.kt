package com.example.yichen.yichen_kwokwing_comp304sec001_lab03.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.navigation.Screen
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.viewmodel.WeatherViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoriteLocationsScreen(navController: NavController) {
    val weatherViewModel: WeatherViewModel = koinViewModel()
    LaunchedEffect(Unit) {
        weatherViewModel.getFavoriteLocations()
    }

    Box(Modifier.safeDrawingPadding()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Button(onClick = { navController.popBackStack() }) {
                Text("Back")
            }

            LazyColumn {
                items(weatherViewModel.favoriteLocations.value) { weather ->
                    WeatherCard(weather) {
                        navController.navigate(
                            Screen.WeatherDetail.route.replace(
                                "{location}",
                                weather.name
                            )
                        )
                    }
                }
            }
        }
    }
}
