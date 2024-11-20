package com.example.yichen.yichen_kwokwing_comp304sec001_lab04.di

import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.data.LocationRepository
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.viewmodel.LocationViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    single<LocationRepository> { LocationRepository() }
    viewModel{ LocationViewModel(get()) }

}

