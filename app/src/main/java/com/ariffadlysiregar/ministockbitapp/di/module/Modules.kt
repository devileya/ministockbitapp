package com.ariffadlysiregar.ministockbitapp.di.module

import android.app.Application
import android.content.Context
import android.webkit.CookieManager
import com.ariffadlysiregar.ministockbitapp.R
import org.koin.dsl.module

object Modules {
    private val applicationModules = module {
        single {
            get<Application>().run {
                getSharedPreferences(
                    getString(R.string.app_name),
                    Context.MODE_PRIVATE
                )
            }
        }

        single {
            CookieManager.getInstance().apply { setAcceptCookie(true) }
        }
    }

    fun getAll() = listOf(
        applicationModules,
        NetworkModules.modules,
        RepositoryModules.modules,
        ViewModelModules.modules
    )
}