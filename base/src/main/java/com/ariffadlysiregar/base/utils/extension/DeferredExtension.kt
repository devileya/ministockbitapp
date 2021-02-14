package com.ariffadlysiregar.base.utils.extension

import com.ariffadlysiregar.base.model.ApiResult
import com.ariffadlysiregar.base.model.DataState
import com.ariffadlysiregar.base.model.remote.response.DefaultErrorResponse
import com.ariffadlysiregar.base.utils.RequestException
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Deferred
import retrofit2.Response
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

suspend inline fun <T> Deferred<Response<T>>.awaitAndGet(): ApiResult<T?> {
    return try {
        val response = await()
        if (response.isSuccessful) ApiResult.Success(response.body(), response.code())
        else {
            val errorBody = response.errorBody()?.source()?.readUtf8()
            Timber.e(errorBody)
            val moshi = Moshi.Builder().build()

            val jsonAdapter = moshi.adapter(DefaultErrorResponse::class.java)
            val errorStatus = try {
                jsonAdapter.fromJson(errorBody)?.status
            } catch (e: Exception) {
                null
            }

            val errorMessage = try {
                jsonAdapter.fromJson(errorBody)?.messages
            } catch (e: Exception) {
                null
            }

            ApiResult.Failure(
                RequestException(
                    response.code(), errorStatus, errorMessage
                )
            )
        }
    } catch (e: Exception) {
        when (e) {
            is UnknownHostException,
            is SocketException,
            is SocketTimeoutException,
            is TimeoutException,
            is ConnectException -> {
            }
            else -> Timber.tag("Network Request").e(e)
        }
        ApiResult.Failure(e)
    }
}

fun <T> toFailureDataState(exception: Throwable) = when (exception) {
    is UnknownHostException,
    is SocketException,
    is SocketTimeoutException,
    is TimeoutException,
    is ConnectException -> DataState.Failure(
        null,
        "Internet connection not available",
        null,
        null
    )
    is RequestException -> DataState.Failure(
        exception.errorCode,
        if (exception.localizedMessage.isEmpty()) "We are experiencing problem to connect to our server, please try again later&#8230;" else exception.localizedMessage,
        exception.getLocalizedErrorMessage(),
        exception.getLocalizedStatus()
    )
    else -> DataState.Failure<T>(
        exception.errorCode,
        "We are experiencing problem to connect to our server, please try again later&#8230;",
        null,
        null
    )
}