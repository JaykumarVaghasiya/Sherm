package com.jay.shermassignment.ui.login

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
    private lateinit var progressBar: CircularProgressIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initializeId()
        progressBar.visibility = View.VISIBLE
        sessionManager = SessionManager(this)
        retrofitInstance = RetrofitInstance
        progressBar.bringToFront()
        btClickListener()


    }
    private fun initializeId(){
        progressBar = findViewById(R.id.progressbar)

        emailEditText = findViewById(R.id.et_email)
        passwordEditText = findViewById(R.id.et_password)
        login = findViewById(R.id.bt_login)
    }
    private fun btClickListener(){
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
                   showToast(this@LoginActivity,e.message)
                    return@launch
                } catch (e: HttpException) {
                    progressBar.visibility = View.GONE
                    showToast(this@LoginActivity,e.message)
                    return@launch
                }

                if (response.isSuccessful && response.body() != null) {
                    val userResponse = response.body()
                    userResponse?.content?.token?.let { sessionManager.saveAuthToken(it) }
                    showToast(this@LoginActivity,R.string.login.toString())
                    progressBar.visibility = View.GONE
                    startActivity(intent)
                    overridePendingTransition(
                        com.google.android.material.R.anim.abc_grow_fade_in_from_bottom,
                        R.anim.slide_out_to_left
                    )
                    finish()
                } else {
                    showErrorDialog()
                }
                progressBar.visibility = View.GONE
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