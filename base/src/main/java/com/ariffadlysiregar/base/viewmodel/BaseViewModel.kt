package com.ariffadlysiregar.base.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ariffadlysiregar.base.R
import com.ariffadlysiregar.base.model.ApiResult
import com.ariffadlysiregar.base.model.DataState
import com.ariffadlysiregar.base.utils.RequestException
import com.ariffadlysiregar.base.utils.extension.errorCode
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

open class BaseViewModel(private val app: Application) :
    AndroidViewModel(app) {

    val forceLogout = MutableLiveData<Int>()

    open fun doOnViewAttached() {}

    open fun onConnectedToNetwork(isConnected: Boolean) {}

    override fun onCleared() {
        super.onCleared()
    }

    protected fun <T> ApiResult.Failure<*>.toFailureDataState() = when (exception) {
        is UnknownHostException,
        is SocketException,
        is SocketTimeoutException,
        is TimeoutException,
        is ConnectException -> DataState.Failure(
            12012,
            app.getString(R.string.no_connection),
            null,
            null
        )
        is RequestException -> {

            DataState.Failure(
                exception.errorCode,
                if (exception.localizedMessage.isEmpty()) app.getString(R.string.default_error_network) else exception.localizedMessage,
                exception.getLocalizedStatus(),
                exception.getLocalizedErrorMessage()
            )

        }
        else -> DataState.Failure<T>(
            exception.errorCode,
            app.getString(R.string.default_error_network),
            null,
            null
        )

    }
}