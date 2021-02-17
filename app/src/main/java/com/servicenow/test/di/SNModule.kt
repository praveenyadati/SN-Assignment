package com.servicenow.test.di

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.room.Room
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.servicenow.test.network.JokesAPI
import com.servicenow.test.network.JokesRepository
import com.servicenow.test.network.JokesRepositoryImpl
import com.servicenow.test.room.JokesDAO
import com.servicenow.test.room.JokesDataBase
import com.servicenow.test.viewmodel.JokesViewModel
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val viewModelModule = module {

    viewModel { (handle: SavedStateHandle) -> JokesViewModel(handle, get()) }
}

val apiModule = module {
    fun provideJokesApi(retrofit: Retrofit): JokesAPI = retrofit.create(JokesAPI::class.java)

    single { provideJokesApi(get()) }
}

val networkModule = module {

    val BASE_URL = "https://api.icndb.com/"

    fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }

    fun provideRetrofit(gson : Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory()).build()
    }

    fun provideOkHttpClient(cache : Cache): OkHttpClient {
        return OkHttpClient().newBuilder().readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS).cache(cache).build()
    }


    single { provideCache(application = androidApplication()) }
    single { provideOkHttpClient(cache = get()) }
    single { provideGson() }
    single { provideRetrofit(gson = get(), okHttpClient = get()) }
}


val databaseModule = module {

    fun provideDatabase(application: Application): JokesDataBase {
        return Room.databaseBuilder(application, JokesDataBase::class.java, "sn.database")
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
