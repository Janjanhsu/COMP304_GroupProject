package com.example.yichen.yichen_kwokwing_comp304sec001_lab03.di

import androidx.room.Room
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.data.local.WeatherDatabase
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.data.remote.WeatherApi
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.data.repository.WeatherRepository
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.viewmodel.WeatherViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}
val appModules = module {
    single<WeatherRepository> { WeatherRepository(get(), get()) }
    single { Dispatchers.IO }
    single { WeatherApi.invoke() }
    single { WeatherDatabase.invoke(get()) }
    single { get<WeatherDatabase>().weatherDao() }
    viewModel{ WeatherViewModel(get()) }

}