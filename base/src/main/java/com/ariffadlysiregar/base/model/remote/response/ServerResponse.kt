package com.ariffadlysiregar.base.model.remote.response

import com.squareup.moshi.Json

data class ServerResponse<T>(
    @Json(name = "Message")
    val messages: String,
    @Json(name = "Data")
    val data: T
)