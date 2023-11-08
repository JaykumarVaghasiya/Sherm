package com.jay.shermassignment.ui.login

import com.jay.shermassignment.utils.NetworkModule
import okhttp3.OkHttpClient

object UserDetailsInstance {
    val api: LoginApi by lazy {
        NetworkModule.provideRetrofit(OkHttpClient())
            .create(LoginApi::class.java)
    }
}