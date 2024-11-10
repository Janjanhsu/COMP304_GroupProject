package com.example.yichen.yichen_kwokwing_comp304sec001_lab03.model

import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.data.local.WeatherEntity

data class WeatherResponse(
    val location: Location,
    val current: Current
)
/*
fun WeatherResponse.toWeatherEntity(): WeatherEntity {
    return WeatherEntity(
        id = null,  // Let Room generate the ID
        location = Location(
            country = location.country,
            lat = location.lat,
            lon = location.lon,
            name = location.name,
            region = location.region,
            localtime = location.localtime,
            localtime_epoch = location.localtime_epoch,
            tz_id = location.tz_id
        ),
        current = Current(temp_c = current.temp_c),
        isFavorite = false
    )
}

 */

fun WeatherResponse.toWeather(): Weather {
    return Weather(
        id = null,
        country = location.country,
        lat = location.lat,
        localtime = location.localtime,
        localtime_epoch = location.localtime_epoch,
        lon = location.lon,
        name = location.name,
        region = location.region,
        tz_id = location.tz_id,
        temp_c = current.temp_c,
        isFavorite = false
    )
}
