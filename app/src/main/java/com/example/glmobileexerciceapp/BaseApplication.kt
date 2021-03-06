package com.example.glmobileexerciceapp

import android.app.Application
import com.example.di.serviceModule
import com.example.di.useCaseModule
import com.example.glmobileexerciceapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            modules(listOf(viewModelModule, useCaseModule, serviceModule))
        }
    }
}
