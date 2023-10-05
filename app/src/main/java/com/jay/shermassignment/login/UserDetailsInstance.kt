package com.jay.shermassignment.login

import com.jay.shermassignment.RetrofitInstance

object UserDetailsInstance {
    val api: LoginApi by lazy {
        RetrofitInstance.api
            .create(LoginApi::class.java)
    }
}