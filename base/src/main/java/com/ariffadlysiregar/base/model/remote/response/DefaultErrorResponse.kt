package com.ariffadlysiregar.base.model.remote.response

import com.squareup.moshi.Json

data class DefaultErrorResponse(
    @Json(name = "status") val status: String,
    @Json(name = "messages") val messages: String
)