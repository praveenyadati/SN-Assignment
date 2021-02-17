package com.servicenow.test

import android.app.Application
import com.servicenow.test.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SNApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@SNApplication)
            androidFileProperties()
            koin.loadModules(listOf(viewModelModule, apiModule, networkModule, databaseModule, repositoryModule))
            koin.createRootScope()
        }
    }
}