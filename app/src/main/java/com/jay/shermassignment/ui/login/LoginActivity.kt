package com.jay.shermassignment.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.jay.shermassignment.R
import com.jay.shermassignment.generic.showCustomDialog
import com.jay.shermassignment.generic.showToast
import com.jay.shermassignment.ui.dashboardUI.MainActivity
import com.jay.shermassignment.utils.RetrofitInstance
import com.jay.shermassignment.utils.SessionManager
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class LoginActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var login: MaterialButton
    private lateinit var sessionManager: SessionManager
    private lateinit var retrofitInstance: RetrofitInstance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initializeViews()
        sessionManager = SessionManager(this)
        retrofitInstance = RetrofitInstance
        setupListeners()
    }

    private fun initializeViews() {

        emailEditText = findViewById(R.id.et_email)
        passwordEditText = findViewById(R.id.et_password)
        login = findViewById(R.id.bt_login)
    }

    private fun setupListeners() {
        login.setOnClickListener { loginUser() }
    }

    private fun loginUser() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val intent = Intent(this, MainActivity::class.java)

        if (email.isEmpty() && password.isEmpty()) {
            showEmptyDialog()
        } else {
            val user = UserDetails(email, password)
            lifecycleScope.launch {
                try {

                    val response = UserDetailsInstance.api.getUserDetails(user)
                    if (response.isSuccessful && response.body() != null) {
                        val userResponse = response.body()
                        userResponse?.content?.token?.let { sessionManager.saveAuthToken(it) }
                        showToast( getString(R.string.login))

                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                        startActivity(intent)
                        finish()
                    }
                    else {
                        showErrorDialog()
                    }
                } catch (e: IOException) {
                    showToast( e.message)
                } catch (e: HttpException) {
                    showToast( e.message)
                }
            }
        }
    }

    private fun showErrorDialog() {
        showCustomDialog(
            R.string.invalid_credentials,
            R.string.request_valid_email_or_password
        )
    }

    private fun showEmptyDialog() {
        showCustomDialog(
            R.string.empty_credentials,
            R.string.request_email_or_password
        )
    }
}
