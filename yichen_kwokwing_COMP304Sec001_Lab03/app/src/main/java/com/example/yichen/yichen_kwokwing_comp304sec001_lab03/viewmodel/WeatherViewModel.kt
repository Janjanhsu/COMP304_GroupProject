package com.example.yichen.yichen_kwokwing_comp304sec001_lab03.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.data.repository.WeatherRepository
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.model.Weather
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {
    private val _weatherState = MutableStateFlow<Resource<Weather>>(Resource.Loading())
    val weatherState: StateFlow<Resource<Weather>> = _weatherState

    private val _favoriteLocations = MutableStateFlow<List<Weather>>(emptyList())
    val favoriteLocations: StateFlow<List<Weather>> = _favoriteLocations

    fun getWeatherForLocation(location: String) {
        viewModelScope.launch {
            _weatherState.value = Resource.Loading()
            _weatherState.value = repository.getWeatherForLocation(location)
        }
    }

    fun getFavoriteLocations() {
        viewModelScope.launch {
            repository.getFavoriteLocations()
                .collect { weatherList ->
                    _favoriteLocations.value = weatherList
            }
        }
    }

    fun addFavoriteLocation(weather: Weather) {
        viewModelScope.launch {
            repository.addFavoriteLocation(weather)
            //getFavoriteLocations()
        }
    }

    fun removeFavoriteLocation(weather: Weather) {
        viewModelScope.launch {
            repository.removeFavoriteLocation(weather)
            //getFavoriteLocations()
        }
    }
}