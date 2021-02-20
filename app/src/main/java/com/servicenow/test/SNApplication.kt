package com.servicenow.test

import com.servicenow.baseframework.BaseApplication
import com.servicenow.test.di.apiModule
import com.servicenow.test.di.databaseModule
import com.servicenow.test.di.repositoryModule
import com.servicenow.test.di.viewModelModule
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.context.unloadKoinModules

class SNApplication : BaseApplication() {

    val modules = listOf(viewModelModule, apiModule, databaseModule, repositoryModule)

    override fun onCreate() {
        super.onCreate()
        loadKoinModules(modules)
    }

    override fun onTerminate() {
        super.onTerminate()
        unloadKoinModules(modules)
        stopKoin()
    }
}