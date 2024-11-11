package com.example.yichen.yichen_kwokwing_comp304sec001_lab03.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.data.local.WeatherDao
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.data.remote.WeatherApi
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.model.Weather
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.model.toWeather
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.model.toWeatherEntity
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WeatherRepository(
    private val weatherApi: WeatherApi,
    private val weatherDao: WeatherDao
) {
    suspend fun getWeatherForLocation(location: String): Resource<Weather> {
        return try {
            val response = weatherApi.getWeather(location=location)
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
        return weatherDao.getAllWeather()
            .map { weatherEntities ->
                weatherEntities.filter { it.isFavorite }
                    .map { it.toWeather() }
            }
    }

    suspend fun addFavoriteLocation(weather: Weather) {
        weatherDao.insertWeather(weather.toWeatherEntity())
    }

    suspend fun removeFavoriteLocation(weather: Weather) {
        weatherDao.deleteWeather(weather.toWeatherEntity())
    }
}
