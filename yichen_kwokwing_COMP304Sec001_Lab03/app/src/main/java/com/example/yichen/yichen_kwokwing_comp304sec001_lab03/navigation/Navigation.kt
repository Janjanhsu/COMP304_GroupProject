package com.example.yichen.yichen_kwokwing_comp304sec001_lab03.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.view.*
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.viewmodel.WeatherViewModel

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object WeatherDetail : Screen("weather_detail/{location}/{isFavorite}"){
        fun createRoute(location:String, isFavorite: Boolean) = "weather_detail/$location/$isFavorite"
    }
    object FavoriteLocations : Screen("favorite_locations")
}

@Composable
fun WeatherNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(
            Screen.WeatherDetail.route,
            arguments = listOf(navArgument("location") { type = NavType.StringType })
        ) { backStackEntry ->
            val location = backStackEntry.arguments?.getString("location") ?: ""
            WeatherDetailScreen(location, navController)
        }
        composable(Screen.FavoriteLocations.route) {
            FavoriteLocationsScreen(navController)
        }
    }
}