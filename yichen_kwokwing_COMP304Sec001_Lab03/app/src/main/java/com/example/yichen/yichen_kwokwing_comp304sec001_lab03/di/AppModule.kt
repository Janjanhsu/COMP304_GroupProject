package com.example.yichen.yichen_kwokwing_comp304sec001_lab03.di

/*
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.data.local.WeatherDatabase
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.data.remote.WeatherApi
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.data.repository.WeatherRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherDatabase(@ApplicationContext context: Context): WeatherDatabase {
        return WeatherDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        weatherApi: WeatherApi,
        weatherDatabase: WeatherDatabase
    ): WeatherRepository {
        return WeatherRepository(weatherApi, weatherDatabase.weatherDao())
    }
}

 */