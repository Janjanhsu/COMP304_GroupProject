package com.example.yichen.yichen_kwokwing_comp304sec001_lab03.model

import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.data.local.WeatherEntity

data class WeatherResponse(
    val location: Location,
    val current: Current
)

fun WeatherResponse.toWeather(existingFavorite: Boolean = false): Weather {
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
        isFavorite = existingFavorite
    )
}
