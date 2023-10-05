package com.jay.shermassignment.login

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.jay.shermassignment.R
import com.jay.shermassignment.RetrofitInstance
import com.jay.shermassignment.SessionManager
import com.jay.shermassignment.dashboardUI.MainActivity
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class LoginActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var login: MaterialButton
    private lateinit var sessionManager: SessionManager
    private lateinit var retrofitInstance: RetrofitInstance
    private lateinit var progressBar: CircularProgressIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sessionManager = SessionManager(this)
        retrofitInstance = RetrofitInstance
        progressBar = findViewById(R.id.progressbar)
        emailEditText = findViewById(R.id.et_email)
        passwordEditText = findViewById(R.id.et_password)
        login = findViewById(R.id.bt_login)

        login.setOnClickListener {
            loginUser()
        }
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

                val response = try {
                    progressBar.visibility = View.VISIBLE
                    UserDetailsInstance.api.getUserDetails(user)
                } catch (e: IOException) {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_SHORT).show()
                    return@launch
                } catch (e: HttpException) {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_SHORT).show()
                    return@launch
                }

                if (response.isSuccessful && response.body() != null) {
                    val userResponse = response.body()
                    userResponse?.content?.token?.let { sessionManager.saveAuthToken(it) }
                    Toast.makeText(this@LoginActivity, R.string.logged_In, Toast.LENGTH_SHORT)
                        .show()
                    progressBar.visibility = View.GONE
                    startActivity(intent)
                    finish()
                } else {
                    showErrorDialog()
                }
            }
        }
    }

    private fun showErrorDialog() {

        val dialogBuilder = AlertDialog.Builder(this)

        dialogBuilder.setMessage(R.string.request_valid_email_or_password)
        dialogBuilder.setTitle(R.string.invalid_credentials)
        dialogBuilder.setCancelable(true)
        dialogBuilder.setPositiveButton("OK") { builder, _ ->
            builder.dismiss()
        }

        val dialog = dialogBuilder.create()
        dialog.show()
    }

    private fun showEmptyDialog() {

        val builder = AlertDialog.Builder(this)

        builder.setMessage(R.string.request_email_or_password)
        builder.setTitle(R.string.empty_credentials)
        builder.setCancelable(true)
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }
}