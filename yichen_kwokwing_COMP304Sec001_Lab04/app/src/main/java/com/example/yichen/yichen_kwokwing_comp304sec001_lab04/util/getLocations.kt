package com.example.yichen.yichen_kwokwing_comp304sec001_lab04.util

import android.content.Context
import androidx.navigation.NavController
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.R
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.data.Locations
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.data.readCsv
import java.io.InputStream

fun getLocations (navController: NavController): Locations {
    val context: Context = navController.context
    val inputStream: InputStream = context.getResources().openRawResource(R.raw.attractions)

    val locations = readCsv(inputStream)
    return locations
}