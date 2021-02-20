package com.servicenow.test.di

import android.app.Application
import androidx.room.Room
import com.servicenow.test.network.JokesAPI
import com.servicenow.test.network.JokesRepository
import com.servicenow.test.network.JokesRepositoryImpl
import com.servicenow.test.room.JokesDAO
import com.servicenow.test.room.JokesDataBase
import com.servicenow.test.viewmodel.JokesViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.inject
import retrofit2.Retrofit


val BASE_URL = "https://api.icndb.com/"


val viewModelModule = module {

    viewModel { JokesViewModel(get()) }
}

val apiModule = module {

    fun provideJokesApi(): JokesAPI {
        val retrofit : Retrofit by inject(Retrofit::class.java) { parametersOf(BASE_URL) }
        return retrofit.create(JokesAPI::class.java)
    }

    single { provideJokesApi() }
}

val databaseModule = module {

    fun provideDatabase(application: Application): JokesDataBase {
        return Room.databaseBuilder(application, JokesDataBase::class.java, "sn_test.database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }


    fun provideDao(database: JokesDataBase): JokesDAO {
        return database.jokesDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
}

val repositoryModule = module {
    fun provideJokesRepository(jokesAPI: JokesAPI, jokesDAO: JokesDAO): JokesRepository {
        return JokesRepositoryImpl(jokesAPI, jokesDAO)
    }

    single { provideJokesRepository(get(), get()) }
}
