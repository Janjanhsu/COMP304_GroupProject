package com.example.yichen.yichen_kwokwing_comp304sec001_lab03.data.remote


import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.model.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    suspend fun getWeather(@Query("q") location: String): Response<WeatherResponse>
}
