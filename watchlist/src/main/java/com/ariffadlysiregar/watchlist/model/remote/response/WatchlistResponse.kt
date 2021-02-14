package com.ariffadlysiregar.watchlist.model.remote.response

import com.squareup.moshi.Json

data class WatchlistResponse(
    @Json(name = "CoinInfo")
    val coinInfo: CoinInfo? = null,
    @Json(name = "DISPLAY")
    val display: Display? = null
)

data class CoinInfo(
    @Json(name = "Id")
    val id: String,
    @Json(name = "Name")
    val name: String,
    @Json(name = "FullName")
    val fullName: String,
    @Json(name = "ImageUrl")
    val imageUrl: String
)

data class Display(
    @Json(name = "IDR")
    val idr: IDR
)

data class IDR(
    @Json(name = "PRICE")
    val price: String,
    @Json(name = "CHANGEPCTDAY")
    val rate: Double
)