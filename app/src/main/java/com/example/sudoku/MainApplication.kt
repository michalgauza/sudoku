package com.example.sudoku

import android.app.Application
import com.example.sudoku.di.restApiModule
import com.example.sudoku.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(listOf(viewModelsModule, restApiModule))
        }
    }
}