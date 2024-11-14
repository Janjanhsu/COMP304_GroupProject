package com.example.yichen.yichen_kwokwing_comp304sec001_lab03.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.data.local.WeatherEntity

data class Weather(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val tz_id: String,
    val localtime_epoch: Int,
    val localtime: String,
    val temp_c: Double,
    val condition: Condition,
    val humidity: Int,
    val cloud: Int,
    val feelslike_c: Double,
    var isFavorite: Boolean
) {
    companion object {
        fun default() = Weather(
            "London",
            "City of London, Greater London",
            "United Kingdom",
            51.5171,
            -0.1062,
            "Europe/London",
            1731262601,
            "2024-11-10 18:16",
            8.3,
            Condition(icon = "//cdn.weatherapi.com/weather/64x64/night/113.png"),
            87,
            0,
            7.7,
            false
        ) // Default weather object
    }
}

fun Weather.toWeatherEntity(): WeatherEntity {
    return WeatherEntity(
        name = name,
        location = Location(name, region, country, lat, lon, tz_id, localtime_epoch, localtime),
        current = Current(temp_c, Condition(condition.icon), humidity, cloud, feelslike_c),
        isFavorite = isFavorite
    )
}
fun Weather.toWeatherEntity(weatherEntity: WeatherEntity): WeatherEntity {
    weatherEntity.location.localtime_epoch = this.localtime_epoch
    weatherEntity.location.localtime = this.localtime
    weatherEntity.current.temp_c = this.temp_c
    weatherEntity.current.condition.icon = this.condition.icon
    weatherEntity.current.humidity = this.humidity
    weatherEntity.current.cloud = this.cloud
    weatherEntity.current.feelslike_c = this.feelslike_c
    weatherEntity.isFavorite = this.isFavorite
    return weatherEntity
}
fun WeatherEntity.toWeather(): Weather {
    return Weather(
        country = location.country,
        lat = location.lat,
        localtime = location.localtime,
        localtime_epoch = location.localtime_epoch,
        lon = location.lon,
        name = location.name,
        region = location.region,
        tz_id = location.tz_id,
        temp_c = current.temp_c,
        condition = Condition(current.condition.icon),
        humidity = current.humidity,
        cloud =  current.cloud,
        feelslike_c = current.feelslike_c,
        isFavorite = isFavorite
    )
}



