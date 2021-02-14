package com.ariffadlysiregar.watchlist.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ariffadlysiregar.base.model.DataState
import com.ariffadlysiregar.base.model.remote.response.ServerResponse
import com.ariffadlysiregar.base.utils.Event
import com.ariffadlysiregar.base.viewmodel.BaseScopeViewModel
import com.ariffadlysiregar.base.viewmodel.BaseScopeViewModel.Companion.loadingState
import com.ariffadlysiregar.watchlist.model.remote.response.WatchlistResponse
import com.ariffadlysiregar.watchlist.repository.WatchlistRepository
import com.ariffadlysiregar.watchlist.utils.TestCoroutineDispatcherRule
import com.jraska.livedata.test
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class WatchlistViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineRule = TestCoroutineDispatcherRule()

    private val application: Application = mockk()
    private val repository: WatchlistRepository = mockk()

    @ExperimentalCoroutinesApi
    private val viewModel: WatchlistViewModel = WatchlistViewModel(
        application,
        repository
    )

    private val stateObserver: Observer<Event<DataState<List<WatchlistResponse>>>> =
        mockk(relaxed = true)
    private val loadingStateObserver: Observer<Boolean> = mockk(relaxed = true)

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        viewModel.watchlistLiveData.observeForever(stateObserver)
        loadingState.observeForever(loadingStateObserver)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when get watchlist is success - should return watchlist data`() =
        coroutineRule.runBlockingTest {
            val limit = 50
            val currency = "IDR"

            val response: Response<ServerResponse<List<WatchlistResponse>>> = mockk()
            val responseCode = 200
            val watchlistResponse: ServerResponse<List<WatchlistResponse>> = mockk()
            val watchlistData: List<WatchlistResponse> = listOf(WatchlistResponse())

            every { watchlistResponse.data } returns watchlistData
            every { response.isSuccessful } returns true
            every { response.body() } returns watchlistResponse
            every { response.code() } returns responseCode
            every { repository.getWatchlistAsync(limit, currency) } returns CompletableDeferred(response)

            viewModel.getWatchlist(limit)

            verify(exactly = 1) {
                loadingStateObserver.onChanged(true)
                repository.getWatchlistAsync(limit, currency)
                stateObserver.onChanged(any())
                loadingStateObserver.onChanged(false)
            }

            viewModel.watchlistLiveData
                .test()
                .assertValue {
                    (it.peekContent() as DataState.Success)
                        .data == watchlistData
                }
        }
}