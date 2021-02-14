package com.ariffadlysiregar.watchlist.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ariffadlysiregar.base.model.ApiResult
import com.ariffadlysiregar.base.model.DataState
import com.ariffadlysiregar.base.utils.Event
import com.ariffadlysiregar.base.utils.extension.awaitAndGet
import com.ariffadlysiregar.base.viewmodel.BaseScopeViewModel
import com.ariffadlysiregar.watchlist.model.remote.response.WatchlistResponse
import com.ariffadlysiregar.watchlist.repository.WatchlistRepository
import kotlinx.coroutines.launch

class WatchlistViewModel(
    val app: Application,
    private val repo: WatchlistRepository
) : BaseScopeViewModel(app) {

    val watchlistLiveData = MutableLiveData<Event<DataState<List<WatchlistResponse>>>>()

    fun getWatchlist(limit: Int) {
        loadingState.postValue(true)
        launch {
            when (val result = repo.getWatchlistAsync(limit).awaitAndGet()) {
                is ApiResult.Success -> {
                    result.body?.let {
                        Event(DataState.Success(it.data))
                    }.run(watchlistLiveData::postValue)
                    loadingState.postValue(false)
                }
                is ApiResult.Failure -> {
                    Event(result.toFailureDataState<List<WatchlistResponse>>())
                        .run(watchlistLiveData::postValue)
                    loadingState.postValue(false)
                }
            }
        }
    }
}