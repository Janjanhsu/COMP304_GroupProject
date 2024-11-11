package com.example.yichen.yichen_kwokwing_comp304sec001_lab03.utils

import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.data.remote.WeatherApi
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.model.toWeather
import kotlinx.coroutines.runBlocking

fun main() {
    val weatherApi = WeatherApi()

    runBlocking {
        try {
            val response = weatherApi.getWeather(location = "Toronto")

            if (response.isSuccessful) {
                val weatherData = response.body()
                if (weatherData != null) {
                    // Print the full object for an overview
                    println("Full Weather Data: $weatherData")

                    // Print individual properties to confirm data parsing
                    println("Location: ${weatherData.toWeather().name}")
                    println("Temperature: ${weatherData.toWeather().temp_c}")
                    println("Favorite: ${weatherData.toWeather().isFavorite}")
                } else {
                    println("No data found.")
                }
            } else {
                println("API call failed with code: ${response.code()}")
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }
}