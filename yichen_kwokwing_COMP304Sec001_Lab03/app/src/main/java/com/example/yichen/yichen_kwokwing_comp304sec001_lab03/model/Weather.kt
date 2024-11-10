package com.example.yichen.yichen_kwokwing_comp304sec001_lab03.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.data.local.WeatherEntity


@Entity(tableName = "weather")
data class Weather(
    @PrimaryKey val id: String,
    val location: String,
    val temperature: Double,
    val description: String,
    val timestamp: Long
)

fun Weather.toWeatherEntity(): WeatherEntity {
    return WeatherEntity(
        id = id,
        location = location,
        temperature = temperature,
        description = description,
        timestamp = timestamp
    )
}

fun WeatherEntity.toWeather(): Weather {
    return Weather(
        id = id,
        location = location,
        temperature = temperature,
        description = description,
        timestamp = timestamp
    )
}


