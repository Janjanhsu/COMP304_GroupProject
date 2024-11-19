package com.example.yichen.yichen_kwokwing_comp304sec001_lab04

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavController

import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.navigation.MapNavHost
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.ui.theme.Yichen_kwokwing_COMP304Sec001_Lab04Theme

class YichenActivity() : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Yichen_kwokwing_COMP304Sec001_Lab04Theme {
                val category = savedInstanceState?.getString("category")

                Log.i("myApp", "category: " +category)
            }

        }
    }
}
