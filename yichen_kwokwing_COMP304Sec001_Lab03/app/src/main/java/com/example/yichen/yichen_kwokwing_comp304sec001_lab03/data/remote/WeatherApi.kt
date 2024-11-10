package com.example.yichen.yichen_kwokwing_comp304sec001_lab03.data.remote


import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.model.WeatherResponse
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("/current.json")
    suspend fun getWeather(
        @Query("apiKey")
        apiKey: String = API_KEY,
        @Query("q")
        location: String
    ): Response<WeatherResponse>
}
