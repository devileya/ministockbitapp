package com.ariffadlysiregar.base.utils.extension

import com.ariffadlysiregar.base.utils.RequestException

fun Throwable.throwException(): Nothing = throw this

val Throwable.errorCode
    get() = when (this) {
        is RequestException -> statusCode
        else -> null
    }