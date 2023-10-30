package com.jay.shermassignment.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
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
    private lateinit var overLay:LinearLayout

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
        overLay=findViewById(R.id.overLay)
        overLay.bringToFront()

    }

    private fun setupListeners() {
        login.setOnClickListener { loginUser() }
    }

    private fun loginUser() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (email.isEmpty() && password.isEmpty()) {
            showEmptyDialog()
        } else {
            val user = UserDetails(email, password)
            overLay.visibility= View.VISIBLE
            lifecycleScope.launch {
                try {

                    val response = UserDetailsInstance.api.getUserDetails(user)
                    if (response.isSuccessful && response.body() != null) {
                        val userResponse = response.body()
                        userResponse?.content?.token?.let { sessionManager.saveAuthToken(it) }

                        startMainActivity()
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
        overLay.visibility= View.GONE
        showCustomDialog(
            R.string.invalid_credentials,
            R.string.request_valid_email_or_password
        )
    }

    private fun showEmptyDialog() {
        overLay.visibility= View.GONE
        showCustomDialog(
            R.string.empty_credentials,
            R.string.request_email_or_password
        )
    }
    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        overLay.visibility= View.GONE
        startActivity(intent)
        finish()
    }
}
