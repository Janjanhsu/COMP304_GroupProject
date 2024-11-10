package com.example.yichen.yichen_kwokwing_comp304sec001_lab03.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherEntity(
    @PrimaryKey val id: String,
    val location: String,
    val temperature: Double,
    val description: String,
    val timestamp: Long
)