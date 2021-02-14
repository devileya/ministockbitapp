package com.ariffadlysiregar.auth.viewModel

import android.app.Application
import com.ariffadlysiregar.auth.repository.AuthRepository
import com.ariffadlysiregar.base.viewmodel.BaseScopeViewModel

class AuthViewModel(
    val app: Application,
    private val authRepo: AuthRepository
) : BaseScopeViewModel(app) {
    fun saveEmail(email: String) = authRepo.saveEmail(email)
    fun getEmail(): String = authRepo.getEmail()
}