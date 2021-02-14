package com.ariffadlysiregar.watchlist.repository

import com.ariffadlysiregar.base.model.remote.response.ServerResponse
import com.ariffadlysiregar.watchlist.api.WatchlistApi
import com.ariffadlysiregar.watchlist.model.remote.response.WatchlistResponse
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Deferred
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class WatchlistRepositoryImplTest {
    private val api: WatchlistApi = mockk(relaxed = true)
    private val repository: WatchlistRepositoryImpl = WatchlistRepositoryImpl(api)

    @Test
    fun `getWatchListAsync() - should call watchlist API`() {
        val limit = 50
        val currency = "IDR"

        val response: Deferred<Response<ServerResponse<List<WatchlistResponse>>>> = mockk()

        every {
            api.getWatchlistAsync(
                limit = limit,
                currency = currency
            )
        } returns response

        val result = repository.getWatchlistAsync(
            limit = limit,
            currency = currency
        )

        verify {
            api.getWatchlistAsync(
                limit = limit,
                currency = currency
            )
        }

        assert(result == response)
    }
}