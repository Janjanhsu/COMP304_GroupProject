package com.example.yichen.yichen_kwokwing_comp304sec001_lab03.view

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.model.Weather
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.navigation.Screen
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.viewmodel.WeatherViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoriteLocationsScreen(navController: NavController) {
    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation
    val weatherViewModel: WeatherViewModel = koinViewModel()

    LaunchedEffect(Unit) {
        weatherViewModel.getFavoriteLocations()
    }

    Box(Modifier.safeDrawingPadding()) {
        Column {
            Text(
                text = "Favorite Locations",
                style = MaterialTheme.typography.titleMedium
            )

            HorizontalDivider(thickness = 2.dp)

            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                LazyColumn {
                    items(weatherViewModel.favoriteLocations.value) { weather ->
                        WeatherCard2(weather, navController) {
                            navController.navigate(
                                Screen.WeatherDetail.route.replace(
                                    "{location}",
                                    weather.name
                                )
                            )
                        }
                    }
                }
            } else{
                val state = rememberLazyStaggeredGridState()

                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    state = state,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    //verticalArrangement = Arrangement.spacedBy(10.dp),
                    content = {
                        items(weatherViewModel.favoriteLocations.value) { weather ->
                            WeatherCard2(weather, navController) {
                                navController.navigate(
                                    Screen.WeatherDetail.route.replace(
                                        "{location}",
                                        weather.name
                                    )
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}