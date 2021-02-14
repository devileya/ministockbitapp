package com.ariffadlysiregar.ministockbitapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ariffadlysiregar.base.viewmodel.BaseScopeViewModel.Companion.loadingState

class MainActivity : AppCompatActivity() {
    private val loadingProgress by lazy { PageLoadingProgress(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadingState.observe(this, Observer { showLoading(it) })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            with(loadingProgress) {
                showFromActivity(this@MainActivity)
                requestFocus()
            }
        } else loadingProgress.hideFromActivity(this)
    }
}