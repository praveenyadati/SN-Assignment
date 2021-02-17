package com.servicenow.test.network

import com.servicenow.test.model.JokesResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface JokesAPI {

    @GET("jokes/random")
    fun getJokes(@Query("firstName") firstName: String?, @Query("lastName") lastName: String?): Deferred<JokesResponse>
}