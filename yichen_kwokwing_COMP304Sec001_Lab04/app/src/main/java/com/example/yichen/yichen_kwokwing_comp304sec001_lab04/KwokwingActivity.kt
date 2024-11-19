package com.example.yichen.yichen_kwokwing_comp304sec001_lab04

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.ui.theme.Yichen_kwokwing_COMP304Sec001_Lab04Theme
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.util.getLocations
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.view.MapScreen

class KwokwingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Yichen_kwokwing_COMP304Sec001_Lab04Theme {

            }

        }
    }

    @Composable
    fun ToMapScreen(location_name: String?, navController: NavController) {
        val locations = getLocations(navController)

        if (location_name != null){
            val location = locations.getLocationByName(location_name)
            MapScreen(location, navController)
        }
    }
}

