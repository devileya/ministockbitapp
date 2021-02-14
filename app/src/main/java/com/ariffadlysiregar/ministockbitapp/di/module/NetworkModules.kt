package com.ariffadlysiregar.ministockbitapp.di.module

import com.ariffadlysiregar.base.network.NetworkBuilder
import com.ariffadlysiregar.ministockbitapp.BuildConfig
import com.ariffadlysiregar.watchlist.api.WatchlistApi
import org.koin.dsl.module

class NetworkModules {
    companion object {
        val modules = module {

            // Watchlist Network
            single<WatchlistApi> {
                NetworkBuilder.create(
                    BuildConfig.BASE_URL,
                    WatchlistApi::class.java
                )
            }

        }
    }
}