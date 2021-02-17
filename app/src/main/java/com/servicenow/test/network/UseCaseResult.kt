package com.servicenow.test.network

sealed class UseCaseResult<out T : Any> {
    class Success<out T : Any>(val data: Array<out T>?) : UseCaseResult<T>()
    class Error(val exception: Throwable) : UseCaseResult<Nothing>()
}