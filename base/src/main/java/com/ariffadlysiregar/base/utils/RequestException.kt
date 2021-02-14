package com.ariffadlysiregar.base.utils

import com.google.gson.annotations.SerializedName

typealias KeyedErrorMap = Map<String, String>

open class RequestException(val statusCode: Int,
                            @SerializedName("status") val status: String?,
                            @SerializedName("message") val errorMessage: String?) : Exception() {

    companion object {
        private const val NO_NETWORK_CODE = -1
    }

    override fun getLocalizedMessage(): String {
        return errorMessage.orEmpty()
    }

    fun getLocalizedStatus():String {
        return status.orEmpty()
    }

    fun getLocalizedErrorMessage(): String {
        return errorMessage.orEmpty()
    }
}