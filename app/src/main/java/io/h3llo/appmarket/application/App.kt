package io.h3llo.appmarket.application

import android.app.Application
import io.h3llo.appmarket.data.AppDatabase

class App : Application() {

    lateinit var instance : AppDatabase

    override fun onCreate() {
        super.onCreate()

        instance = AppDatabase.getInstance(this)
    }
}