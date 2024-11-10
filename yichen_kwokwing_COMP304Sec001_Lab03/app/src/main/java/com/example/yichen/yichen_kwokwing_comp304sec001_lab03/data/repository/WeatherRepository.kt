package com.example.yichen.yichen_kwokwing_comp304sec001_lab03.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.data.local.WeatherDao
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.data.remote.WeatherApi
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.model.Weather
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.model.toWeather
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.model.toWeatherEntity
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.utils.Resource

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

    suspend fun getFavoriteLocations(): LiveData<List<Weather>> {
        val liveDataWeather = weatherDao.getAllWeather()
        // Map and manually include only favorites
        return liveDataWeather.map { weatherEntities ->
            weatherEntities.mapNotNull {
                if (it.isFavorite) it.toWeather() else null
            }
        }
    }

    suspend fun addFavoriteLocation(weather: Weather) {
        weatherDao.insertWeather(weather.toWeatherEntity())
    }

    suspend fun removeFavoriteLocation(weather: Weather) {
        weatherDao.deleteWeather(weather.toWeatherEntity())
    }
}
