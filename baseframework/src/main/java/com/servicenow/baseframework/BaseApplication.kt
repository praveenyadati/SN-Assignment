package com.servicenow.baseframework

import android.app.Application
import com.servicenow.baseframework.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

abstract class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            androidFileProperties()
            koin.loadModules(listOf(networkModule))
            koin.createRootScope()
        }
    }
}