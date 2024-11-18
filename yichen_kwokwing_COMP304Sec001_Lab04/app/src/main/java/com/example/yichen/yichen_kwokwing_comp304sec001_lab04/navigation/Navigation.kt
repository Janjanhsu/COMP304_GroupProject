package com.example.yichen.yichen_kwokwing_comp304sec001_lab04.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.view.*

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object FavoriteDetailScreen : Screen("favorite_detail/{location}"){
        fun createRoute(location:String) = "favorite_detail/$location"
    }
    object MapScreen : Screen("map")
}

@Composable
fun MapNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(
            Screen.FavoriteDetailScreen.route,
            arguments = listOf(
                navArgument("location") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val location = backStackEntry.arguments?.getString("location") ?: ""
            FavoriteDetailScreen(location, navController)
        }
        composable(Screen.MapScreen.route) {
            MapScreen(navController)
        }
    }
}