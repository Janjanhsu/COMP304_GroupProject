package com.example.yichen.yichen_kwokwing_comp304sec001_lab04

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.ui.theme.Yichen_kwokwing_COMP304Sec001_Lab04Theme

import androidx.navigation.compose.rememberNavController
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.navigation.MapNavHost
import java.io.InputStream


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Yichen_kwokwing_COMP304Sec001_Lab04Theme {
                //val my_attractions = getResources().openRawResource(R.raw.attractionscanada);
                MapApp()
            }

        }
    }

}

@Composable
fun MapApp() {
    val navController = rememberNavController()
    MapNavHost(navController)

}