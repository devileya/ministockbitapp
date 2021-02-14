package com.ariffadlysiregar.watchlist.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.ariffadlysiregar.watchlist.R
import com.ariffadlysiregar.watchlist.model.remote.response.WatchlistResponse
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_watch_list.view.*
import timber.log.Timber

class ItemWatchlistAdapter(
    private val data: List<WatchlistResponse>,
    private val listener: (WatchlistResponse) -> Unit
) : androidx.recyclerview.widget.RecyclerView.Adapter<ItemWatchlistAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_watch_list, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindItem(data[position], listener)
    }

    override fun getItemCount(): Int = data.size


    inner class MovieViewHolder(override val containerView: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bindItem(
            data: WatchlistResponse,
            listener: (WatchlistResponse) -> Unit
        ) {
            data.coinInfo?.let {
                itemView.tv_name.text = it.name
                itemView.tv_full_name.text = it.fullName
                Glide.with(itemView.context)
                    .load("https://www.cryptocompare.com${it.imageUrl}")
                    .into(itemView.iv_logo)
            }

            data.display?.idr?.let {
                itemView.tv_price.text = it.price
                itemView.tv_rate.text = it.rate.toString()
                when {
                    it.rate > 0.0 -> {
                        itemView.tv_rate.setTextColor(
                            ContextCompat.getColor(
                                itemView.context,
                                R.color.colorPrimary
                            )
                        )
                        itemView.tv_rate.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            R.drawable.ic_arrow_up,
                            0,
                            0,
                            0
                        )
                    }
                    it.rate < 0.0 -> {
                        itemView.tv_rate.setTextColor(
                            ContextCompat.getColor(
                                itemView.context,
                                R.color.red
                            )
                        )
                        itemView.tv_rate.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            R.drawable.ic_arrow_down,
                            0,
                            0,
                            0
                        )
                    }
                }
            }
            containerView.setOnClickListener { listener(data) }
        }
    }
}