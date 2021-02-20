package com.servicenow.test.network

import com.servicenow.test.model.Joke
import com.servicenow.test.room.JokesDAO

interface JokesRepository {
    suspend fun getJokes(firstName : String?, lastName : String?, addCache : Boolean) : UseCaseResult<Joke>

    suspend fun getJokesFromCache() : UseCaseResult<Joke>
}

class JokesRepositoryImpl(private val jokesAPI: JokesAPI, private val jokesDAO: JokesDAO) : JokesRepository {

    override suspend fun getJokes(firstName : String?, lastName : String?, addCache : Boolean): UseCaseResult<Joke> {
        return try {
            val result = jokesAPI.getJokes(firstName, lastName).await()
            val joke = result.value
            joke.firstName = firstName
            joke.lastName = lastName
            jokesDAO.insert(joke)
            val jokes = if(addCache) jokesDAO.findAll() else null
            UseCaseResult.Success(joke, jokes)
        } catch (ex: Exception) {
            return if(addCache) getJokesFromCache() else UseCaseResult.Error(ex.cause)
        }
    }

    override suspend fun getJokesFromCache(): UseCaseResult<Joke> {
        return try {
            val jokes = jokesDAO.findAll()
            UseCaseResult.Success(jokesList = jokes)
        } catch (ex: Exception) {
            UseCaseResult.Error(ex.cause)
        }
    }
}