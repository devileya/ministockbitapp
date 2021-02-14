package com.ariffadlysiregar.auth.repository

import android.content.SharedPreferences

interface AuthRepository {
    fun saveEmail(email: String)
    fun getEmail(): String
}

class AuthRepositoryImpl(private val sharedPref: SharedPreferences): AuthRepository {
    override fun saveEmail(email: String) {
        sharedPref.edit().putString("email", email).apply()
    }

    override fun getEmail() = sharedPref.getString("email", "").toString()
}