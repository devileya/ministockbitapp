package com.ariffadlysiregar.ministockbitapp.di.module

import com.ariffadlysiregar.auth.repository.AuthRepository
import com.ariffadlysiregar.auth.repository.AuthRepositoryImpl
import com.ariffadlysiregar.watchlist.repository.WatchlistRepository
import com.ariffadlysiregar.watchlist.repository.WatchlistRepositoryImpl
import org.koin.dsl.module

class RepositoryModules {
    companion object {
        val modules = module {
            single<AuthRepository> { AuthRepositoryImpl(get()) }
            single<WatchlistRepository> { WatchlistRepositoryImpl(get()) }
        }
    }
}