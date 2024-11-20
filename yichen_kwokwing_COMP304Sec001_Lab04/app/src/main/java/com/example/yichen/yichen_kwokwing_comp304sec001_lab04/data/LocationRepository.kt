package com.example.yichen.yichen_kwokwing_comp304sec001_lab04.data

import android.util.Log

class LocationRepository {
    private val _locations = mutableListOf<Location>()
    private val _categories = mutableSetOf<String>()

    fun addAll(locations: List<Location>) {
        _locations.addAll(locations)
        _categories.addAll(locations.map { it.category })
    }

    fun getList(): List<Location> = _locations.toList()

    fun getCategory(): List<String> = _categories.toList()

    fun getLocationsByCategory(category: String): List<Location> =
        _locations.filter { it.category == category }

    fun getLocationByName(locationName: String): Location =
        _locations.find { it.name == locationName }
            ?: Location("", "", "", "", 0.0, 0.0)

    fun add(location: Location) {
        _locations.add(location)
        if (!_categories.contains(location.category)) {
            _categories.add(location.category)
        }
    }
}