package com.example.yichen.yichen_kwokwing_comp304sec001_lab03.data.repository

import android.util.Log
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.data.local.WeatherDao
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.data.remote.WeatherApi
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.model.Weather
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.model.toWeather
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.model.toWeatherEntity
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class WeatherRepository(
    private val weatherApi: WeatherApi,
    private val weatherDao: WeatherDao
) {
    suspend fun getWeatherForLocation(location: String): Resource<Weather> {
        return try {
            val response = weatherApi.getWeather(location = location)
            if (response.isSuccessful) {
                val weatherData = response.body()?.toWeather() ?: Weather.default()
                weatherData.let {
                    weatherDao.insertWeather(it.toWeatherEntity())
                }
                Resource.Success(weatherData)
            } else {
                Resource.Error("Failed to fetch weather data")
            }
        } catch (e: Exception) {
            Resource.Error("An error occurred: ${e.message}")
        }
    }

    suspend fun getFavoriteLocations(): Flow<List<Weather>> {
        return weatherDao.getFavoriteLocation().map { weatherEntities ->
            val weatherList = mutableListOf<Weather>()
            for (location in weatherEntities) {
                try {
                    val response = weatherApi.getWeather(location = location.name)
                    if (response.isSuccessful) {
                        val weatherData = response.body()?.toWeather(true) ?: Weather.default()
                        // Insert the fetched data into the database
                        //weatherDao.insertWeather(weatherData.toWeatherEntity())
                        weatherList.add(weatherData)
                    } else {
                        // Log the error or handle it
                        Log.e("WeatherError", "Failed to fetch weather for $location")
                    }
                } catch (e: Exception) {
                    Log.e("WeatherError", "An error occurred for $location: ${e.message}")
                }
            }
            //Log.e("myApp", "AAAAAA: " +weatherList)
            weatherList.sortedBy { it.name } // This returns the list of weather data
        }
    }

    suspend fun addFavoriteLocation(weather: Weather) {
        //weather.isFavorite = true
        weatherDao.addFavoriteLocation(weather.name)
        //Log.i("myApp", "Hello: " + weatherDao.getAllWeather().map { it.filter { it.isFavorite } })
    }

    suspend fun removeFavoriteLocation(weather: Weather) {
        weatherDao.deleteWeather(weather.toWeatherEntity())
    }
}