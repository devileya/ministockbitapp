package com.ariffadlysiregar.watchlist.repository

import com.ariffadlysiregar.base.model.remote.response.ServerResponse
import com.ariffadlysiregar.watchlist.api.WatchlistApi
import com.ariffadlysiregar.watchlist.model.remote.response.WatchlistResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response

interface WatchlistRepository {
    fun getWatchlistAsync(limit: Int, currency: String = "IDR"): Deferred<Response<ServerResponse<List<WatchlistResponse>>>>
}

class WatchlistRepositoryImpl(private val api: WatchlistApi): WatchlistRepository {
    override fun getWatchlistAsync(
        limit: Int,
        currency: String
    ): Deferred<Response<ServerResponse<List<WatchlistResponse>>>> {
        return api.getWatchlistAsync(limit, currency)
    }
}