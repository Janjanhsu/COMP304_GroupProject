package com.example.yichen.yichen_kwokwing_comp304sec001_lab04

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import com.example.yichen.yichen_kwokwing_comp304sec001_lab04.di.appModules

class MapApplication : Application()  {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MapApplication)
            modules(appModules)
        }
    }
}