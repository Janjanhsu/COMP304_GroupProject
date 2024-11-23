package com.example.yichen.yichen_kwokwing_comp304sec001_lab04.data

import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.model.DirectionsResponse
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.util.Constants.Companion.API_KEY
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.util.Constants.Companion.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface DirectionAPI {
    @GET("directions/json")
    suspend fun getDirection(
        @Query("destination")
        destination: String,
        @Query("origin")
        origin: String
    ): Response<DirectionsResponse>

    companion object{
        operator fun invoke(): DirectionAPI{
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
                .create(DirectionAPI::class.java)
        }
    }
}