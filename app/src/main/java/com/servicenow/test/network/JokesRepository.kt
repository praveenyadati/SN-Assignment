package com.servicenow.test.network

import com.servicenow.test.model.Joke
import com.servicenow.test.room.JokesDAO

interface JokesRepository {
    suspend fun getJokes(firstName : String?, lastName : String?) : UseCaseResult<Joke>

    suspend fun getJokesFromCache() : UseCaseResult<Joke>
}

class JokesRepositoryImpl(private val jokesAPI: JokesAPI, private val jokesDAO: JokesDAO) : JokesRepository {

    override suspend fun getJokes(firstName : String?, lastName : String?): UseCaseResult<Joke> {
        return try {
            val result = jokesAPI.getJokes(firstName, lastName).await()
            val value = result.value
            value.firstName = firstName
            value.lastName = lastName
            jokesDAO.insert(value)
            val jokes = jokesDAO.findAll()
            UseCaseResult.Success(jokes)
        } catch (ex: Exception) { //Network connection failure
            //Fetching data from local cache in case if there is no network or API failure
            getJokesFromCache()
        }
    }

    override suspend fun getJokesFromCache(): UseCaseResult<Joke> {
        return try {
            val jokes = jokesDAO.findAll()
            UseCaseResult.Success(jokes)
        } catch (ex: Exception) {
            UseCaseResult.Success(null)
        }
    }
}