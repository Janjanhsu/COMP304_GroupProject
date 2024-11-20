package com.example.yichen.yichen_kwokwing_comp304sec001_lab04.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.data.Location
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.data.LocationRepository
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.util.getLocations
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.asStateFlow

class LocationViewModel(private val repository: LocationRepository) : ViewModel() {
    private val _locations = MutableStateFlow<List<Location>>(emptyList())
    val locations: StateFlow<List<Location>> = _locations.asStateFlow()

    private val _attractionLocation = MutableStateFlow(LatLng(0.0, 0.0))
    val attractionLocation: StateFlow<LatLng> = _attractionLocation.asStateFlow()

    private val _locationCategories = MutableStateFlow<List<String>>(emptyList())
    val locationCategories: StateFlow<List<String>> = _locationCategories.asStateFlow()

    private val _categoryLocations = MutableStateFlow<List<Location>>(emptyList())
    val categoryLocations: StateFlow<List<Location>> = _categoryLocations.asStateFlow()

    init {
        viewModelScope.launch {
            _locationCategories.value = repository.getCategory()
        }
    }

    fun loadLocations(navController: NavController) {
        viewModelScope.launch {
            if (_locations.value.isEmpty()) {
                val context = navController.context
                val loadedLocations = getLocations(context)
                _locations.value = loadedLocations
                repository.addAll(loadedLocations)
                _locationCategories.value = repository.getCategory()
            }
        }
    }

    fun getLocationsByCategory(category: String): List<Location> {
        viewModelScope.launch {
            _categoryLocations.value = repository.getLocationsByCategory(category)
        }
        return categoryLocations.value
    }

    fun updateAttractionLocation(locationName: String) {
        viewModelScope.launch {
            val location = repository.getLocationByName(locationName)
            _attractionLocation.value = LatLng(location.lat, location.long)
        }
    }
}
