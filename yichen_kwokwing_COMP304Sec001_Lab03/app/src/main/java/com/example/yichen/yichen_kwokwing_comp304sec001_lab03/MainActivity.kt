package com.example.yichen.yichen_kwokwing_comp304sec001_lab03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.navigation.WeatherNavHost
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.ui.theme.Yichen_kwokwing_COMP304Sec001_Lab03Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Yichen_kwokwing_COMP304Sec001_Lab03Theme {
                val navController = rememberNavController()
                WeatherNavHost(navController)
            }
        }
    }
}

@Composable
fun WeatherApp(windowSizeClass: WindowSizeClass) {
    val navController = rememberNavController()

    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            WeatherNavHost(navController)
        }
        WindowWidthSizeClass.Medium, WindowWidthSizeClass.Expanded -> {
            Row {
                NavigationRail {
                    // Add navigation items
                }
                WeatherNavHost(navController)
            }
        }
    }
}
