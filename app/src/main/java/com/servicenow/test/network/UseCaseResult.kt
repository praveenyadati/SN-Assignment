package com.servicenow.test.network


sealed class UseCaseResult<out T : Any> {

    data class Success<out T : Any>(val joke: T? = null, val jokesList: Array<out T>? = null) : UseCaseResult<T>()
    class Error(val exception: Throwable?) : UseCaseResult<Nothing>()
}