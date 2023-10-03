package com.jay.shermassignment.login

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UserDetailsInstance {
    val api: LoginApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://staging.shermsoftware.com.au")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LoginApi::class.java)
    }
}