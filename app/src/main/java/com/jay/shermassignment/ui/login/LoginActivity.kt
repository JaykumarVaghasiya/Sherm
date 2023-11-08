package com.jay.shermassignment.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.jay.shermassignment.R
import com.jay.shermassignment.databinding.ActivityLoginBinding
import com.jay.shermassignment.di.viewmodels.LoginViewModel
import com.jay.shermassignment.generic.showConfirmationDialog
import com.jay.shermassignment.ui.dashboardUI.MainActivity
import com.jay.shermassignment.utils.NetworkModule
import com.jay.shermassignment.utils.SessionManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    lateinit var sessionManager: SessionManager

    lateinit var retrofitInstance: NetworkModule

    private lateinit var _binding: ActivityLoginBinding

    lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        sessionManager = SessionManager(this)
        retrofitInstance = NetworkModule
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        _binding.overLay.loadingScreen.bringToFront()
        setupListeners()
    }

    private fun setupListeners() {
        _binding.btLogin.setOnClickListener { loginUser() }
    }

    private fun loginUser() {
        val email = _binding.etEmail.text.toString()
        val password = _binding.etPassword.text.toString()

        if (email.isEmpty() && password.isEmpty()) {
            showEmptyDialog()
        } else {
            val userDetails = UserDetails(email, password)

            _binding.overLay.loadingScreen.visibility = View.VISIBLE
            viewModel.login(userDetails)


            viewModel._userResponseLiveData.observe(this) { userResponse ->
                _binding.overLay.loadingScreen.visibility = View.GONE
                if (userResponse != null) {
                    startMainActivity()
                }
            }
            viewModel._errorMessageLiveData.observe(this) { errorMessage ->
                _binding.overLay.loadingScreen.visibility = View.GONE
                if (errorMessage != null) {
                    showErrorDialog()
                }
            }
        }
    }

    private fun showErrorDialog() {
        _binding.overLay.loadingScreen.visibility = View.GONE
        showConfirmationDialog(
            getString(R.string.invalid_credentials),
            getString(R.string.request_valid_email_or_password)
        )
    }

    private fun showEmptyDialog() {
        _binding.overLay.loadingScreen.visibility = View.GONE
        showConfirmationDialog(
            getString(R.string.empty_credentials),
            getString(R.string.request_email_or_password)
        )
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        _binding.overLay.loadingScreen.visibility = View.GONE
        startActivity(intent)
        finish()
    }
}
