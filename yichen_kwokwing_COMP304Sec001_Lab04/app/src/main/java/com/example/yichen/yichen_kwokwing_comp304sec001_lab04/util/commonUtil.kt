package com.example.yichen.yichen_kwokwing_comp304sec001_lab04.util

import android.content.Context
import android.content.res.Resources
import androidx.navigation.NavController
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.R
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.data.Location
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.data.LocationRepository
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.data.readCsv
import java.io.InputStream


fun getLocations (context: Context): List<Location> {
    val inputStream: InputStream = context.getResources().openRawResource(R.raw.attractions)
    val locations = readCsv(inputStream)
    return locations.getList()
}

fun getDrawableIDByName(name:String, navController: NavController): Int {
    val context: Context = navController.context
    val resources: Resources = context.getResources()
    val resourceId = resources.getIdentifier(
        name, "drawable",
        context.getPackageName()
    )
    return resourceId
}