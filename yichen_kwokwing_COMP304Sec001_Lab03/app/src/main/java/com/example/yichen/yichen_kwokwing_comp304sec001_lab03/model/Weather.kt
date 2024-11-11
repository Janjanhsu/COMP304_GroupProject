package com.example.yichen.yichen_kwokwing_comp304sec001_lab03.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.data.local.WeatherEntity

@Serializable
data class Weather(
    val id: Int? = null,
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val tz_id: String,
    val localtime_epoch: Int,
    val localtime: String,
    val temp_c: Double,
    val isFavorite: Boolean
) {
    companion object {
        fun default() = Weather(
            id = null,
            "London",
            "City of London, Greater London",
            "United Kingdom",
            51.5171,
            -0.1062,
            "Europe/London",
            1731262601,
            "2024-11-10 18:16",
            8.3, false
        ) // Default weather object
    }
}

fun Weather.toWeatherEntity(): WeatherEntity {
    return WeatherEntity(
        id = id,
        location = Location(name, region, country, lat, lon, tz_id, localtime_epoch, localtime),
        current = Current(temp_c),
        isFavorite = isFavorite
    )
}

fun WeatherEntity.toWeather(): Weather {
    return Weather(
        id = id,
        country = location.country,
        lat = location.lat,
        localtime = location.localtime,
        localtime_epoch = location.localtime_epoch,
        lon = location.lon,
        name = location.name,
        region = location.region,
        tz_id = location.tz_id,
        temp_c = current.temp_c,
        isFavorite = isFavorite
    )
}



