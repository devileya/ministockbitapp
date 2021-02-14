package com.ariffadlysiregar.ministockbitapp

import android.app.Application
import com.ariffadlysiregar.ministockbitapp.di.module.Modules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(Modules.getAll())
        }

        initTimberDebug()
    }

    private fun initTimberDebug() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}