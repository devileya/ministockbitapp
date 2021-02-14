package com.ariffadlysiregar.ministockbitapp.di.module

import com.ariffadlysiregar.auth.viewModel.AuthViewModel
import com.ariffadlysiregar.base.viewmodel.BaseScopeViewModel
import com.ariffadlysiregar.watchlist.viewmodel.WatchlistViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class ViewModelModules {
    companion object {
        val modules = module {
            viewModel { AuthViewModel(get(), get()) }
            viewModel { WatchlistViewModel(get(), get()) }
        }
    }
}