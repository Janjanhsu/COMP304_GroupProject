package com.example.yichen.yichen_kwokwing_comp304sec001_lab03

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin
import com.example.yichen.yichen_kwokwing_comp304sec001_lab03.di.appModules

class WeatherApplication: Application()  {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WeatherApplication)
            workManagerFactory()
            modules(appModules)
        }
    }
}