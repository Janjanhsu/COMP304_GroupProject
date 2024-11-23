package com.example.yichen.yichen_kwokwing_comp304sec001_lab04.util

import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.data.DirectionAPI
import kotlinx.coroutines.runBlocking

fun main() {
    val directionApi = DirectionAPI()

    runBlocking {
        try {
            val response = directionApi.getDirection(destination = "Montreal", origin = "Toronto")

            if (response.isSuccessful) {
                val directionData = response.body()
                if (directionData != null) {
                    // Print the full object for an overview
                    println("Full Direction Data: $directionData")

                    // Print individual properties to confirm data parsing
                    println("Location: ${directionData.routes}")
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