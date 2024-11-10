package com.example.yichen.yichen_kwokwing_comp304sec001_lab03.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object WeatherApiService {
    private const val BASE_URL = "http://api.weatherapi.com/v1"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val weatherApi: WeatherApi = retrofit.create(WeatherApi::class.java)
}