package com.example.yichen.yichen_kwokwing_comp304sec001_lab04.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.view.KwokwingActivity
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.view.YichenActivity
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.view.*


sealed class Screen(val route: String) {
    object Home : Screen("home")
    object YichenActivity : Screen("yichen_activity"){
        fun createRoute(category:String) = "yichen_activity/$category"
    }
    object KwokwingActivity : Screen("kwokwing_activity"){
        fun createRoute(attraction:String) = "kwokwing_activity/$attraction"
    }
}
@Composable
fun MapNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(
            route = "${Screen.YichenActivity.route}/{category}",
            arguments = listOf(
                navArgument("category") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: ""
            if (category.isNotEmpty()) {
                YichenActivity(category, navController)
            }
        }
        composable(
            route = "${Screen.KwokwingActivity.route}/{attraction}",
            arguments = listOf(
                navArgument("attraction") {
                    type = NavType.StringType
                    nullable = false
                    defaultValue = ""
                }
            )
        ) {entry->
            val attraction = entry.arguments?.getString("attraction")
            if (attraction != null) {
                KwokwingActivity(attraction, navController)
            }
        }
    }
}