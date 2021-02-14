package com.ariffadlysiregar.watchlist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ariffadlysiregar.base.model.DataState
import com.ariffadlysiregar.base.utils.EventObserver
import com.ariffadlysiregar.watchlist.R
import com.ariffadlysiregar.watchlist.model.remote.response.WatchlistResponse
import com.ariffadlysiregar.watchlist.view.adapter.ItemWatchlistAdapter
import com.ariffadlysiregar.watchlist.viewmodel.WatchlistViewModel
import kotlinx.android.synthetic.main.fragment_watch_list.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class WatchListFragment : Fragment() {

    private val viewModel by sharedViewModel<WatchlistViewModel>()
    private val watchlistData = mutableListOf<WatchlistResponse>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_watch_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getWatchlist(50)
        setupRecyclerView()
        observeData()
        swipe_refresh.setOnRefreshListener { viewModel.getWatchlist(20) }
    }

    private fun observeData() {
        viewModel.watchlistLiveData.observe(viewLifecycleOwner, EventObserver(::showEventList))
    }

    private fun setupRecyclerView() {
        rv_watchlist.apply {
            adapter = ItemWatchlistAdapter(watchlistData) { item ->
                showMessage("Click ${item.coinInfo?.fullName}")
            }
        }
        rv_watchlist.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if ((rv_watchlist.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() == watchlistData.size - 1)
                    viewModel.getWatchlist(watchlistData.size+20)
            }
        })
    }

    private fun showEventList(state: DataState<List<WatchlistResponse>>) {
        when (state) {
            is DataState.Success -> {
                state.data?.let {
                    if (watchlistData.size > it.lastIndex) {
                        watchlistData.clear()
                        watchlistData.addAll(it)
                    } else
                        watchlistData.addAll(it.subList(watchlistData.size, it.lastIndex))
                    rv_watchlist.adapter?.notifyDataSetChanged()
                }
            }
            is DataState.Failure -> {
                showMessage(state.errorMessage)
            }
        }
        swipe_refresh.isRefreshing = false
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}