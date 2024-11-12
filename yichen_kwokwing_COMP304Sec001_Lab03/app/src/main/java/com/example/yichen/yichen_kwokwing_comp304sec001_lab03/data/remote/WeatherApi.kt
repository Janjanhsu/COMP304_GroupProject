package com.example.yichen.yichen_kwokwing_comp304sec001_lab03.data.remote


import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.model.WeatherResponse
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.utils.Constants.Companion.API_KEY
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.utils.Constants.Companion.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("current.json")
    suspend fun getWeather(
        @Query("q")
        location: String,
        @Query("aqi")
        aqi: String = "no"
    ): Response<WeatherResponse>

    companion object{
        operator fun invoke(): WeatherApi{
            val requestInterceptor = Interceptor{
                chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("key", API_KEY)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApi::class.java)
        }
    }
}
