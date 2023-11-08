package com.jay.shermassignment.api.inspection

import com.jay.shermassignment.utils.NetworkModule
import okhttp3.OkHttpClient

object SpinnerInstance {

    val api: SpinnerAPI by lazy {
        NetworkModule.provideRetrofit(OkHttpClient()).create(
            SpinnerAPI::class.java
        )
    }
}