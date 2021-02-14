package com.ariffadlysiregar.auth.view

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.ariffadlysiregar.auth.R
import com.ariffadlysiregar.auth.viewModel.AuthViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LoginFragment : Fragment() {

    private val viewModel by sharedViewModel<AuthViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (viewModel.getEmail().isEmpty().not())
            findNavController().navigate(Uri.parse("miniStockbitApp://watchlist"))

        setupListener()
    }

    private fun setupListener() {
        btn_fb_signin.setOnClickListener { comingSoon() }
        btn_google_signin.setOnClickListener { comingSoon() }
        btn_login_fingerprint.setOnClickListener { comingSoon() }
        tv_signup.setOnClickListener { comingSoon() }
        tv_forgot_password.setOnClickListener { comingSoon() }
        btn_login.setOnClickListener { validateForm() }
    }

    private fun validateForm(){
        val email = et_email.text
        val pass = et_password.text
        when {
            email.isNullOrEmpty() -> et_email.error = "email harus diisi"
            pass.isNullOrEmpty() -> et_password.error = "password harus diisi"
            isValidEmail(email.toString()).not() -> et_email.error = "format email salah"
            else -> {
                viewModel.saveEmail(email.toString())
                findNavController().navigate(Uri.parse("miniStockbitApp://watchlist"))
            }
        }
    }

    private fun comingSoon() {
        Toast.makeText(requireContext(), "Coming Soon", Toast.LENGTH_SHORT).show()
    }

    private fun isValidEmail(email : String) : Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}