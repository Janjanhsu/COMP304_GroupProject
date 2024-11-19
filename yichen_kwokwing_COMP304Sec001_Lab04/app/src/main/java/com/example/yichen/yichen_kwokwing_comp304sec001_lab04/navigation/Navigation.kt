package com.example.yichen.yichen_kwokwing_comp304sec001_lab04.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.KwokwingActivity
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.YichenActivity
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.view.*


sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Yichen_activity : Screen("yichen_acctivity"){
        fun createRoute(category:String) = "yichen_acctivity/$category"
    }
    object Kwokwing_activity : Screen("kwokwing_acctivity"){
        fun createRoute(location:String) = "kwokwing_acctivity/$location"
    }
}

@Composable
fun MapNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }

        composable(
            route = "${Screen.Yichen_activity.route}/{category}",
            arguments = listOf(
                navArgument("category") {
                    type = NavType.StringType
                    nullable = false
                    defaultValue = ""
                }
            )
        ) {entry->
            val category = entry.arguments?.getString("category")
            YichenActivity().LocationScreen(category, navController)
        }

        composable(
            route = "${Screen.Kwokwing_activity.route}/{location}",
            arguments = listOf(
                navArgument("location") {
                    type = NavType.StringType
                    nullable = false
                    defaultValue = ""
                }
            )
        ) {entry->
            val location = entry.arguments?.getString("location")
            KwokwingActivity().ToMapScreen(location, navController)
        }
    }
}