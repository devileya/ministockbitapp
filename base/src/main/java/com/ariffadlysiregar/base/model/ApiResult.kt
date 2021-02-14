package com.ariffadlysiregar.base.model

sealed class ApiResult<T> {
    data class Success<T>(val body: T, val code: Int) : ApiResult<T>()
    data class Failure<T>(val exception: Throwable) : ApiResult<T>()
}