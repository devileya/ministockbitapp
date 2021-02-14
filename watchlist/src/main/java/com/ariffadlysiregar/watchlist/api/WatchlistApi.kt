package com.ariffadlysiregar.watchlist.api

import com.ariffadlysiregar.base.model.remote.response.ServerResponse
import com.ariffadlysiregar.watchlist.model.remote.response.WatchlistResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WatchlistApi {
    @GET("/data/top/totaltoptiervolfull")
    fun getWatchlistAsync(
        @Query("limit") limit: Int,
        @Query("tsym") currency: String
    ): Deferred<Response<ServerResponse<List<WatchlistResponse>>>>
}