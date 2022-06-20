package com.example.potatodisease

import android.app.Application
import com.example.potatodisease.inject.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PotatoDiseaseApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PotatoDiseaseApp)
            modules(appModule)
        }
    }
}