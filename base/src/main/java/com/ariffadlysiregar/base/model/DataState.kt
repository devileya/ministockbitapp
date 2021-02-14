package com.ariffadlysiregar.base.model

sealed class DataState<T> {
    data class Success<T>(val data: T?) : DataState<T>()
    data class Failure<T>(val errorCode: Int?, val errorMessage: String, val errorStatus: String?, val codeStatus: String?) : DataState<T>()
}