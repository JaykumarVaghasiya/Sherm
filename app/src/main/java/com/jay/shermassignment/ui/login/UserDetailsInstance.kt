package com.jay.shermassignment.ui.login

import com.jay.shermassignment.utils.RetrofitInstance

object UserDetailsInstance {
    val api: LoginApi by lazy {
        RetrofitInstance.api
            .create(LoginApi::class.java)
    }
}