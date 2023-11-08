package com.jay.shermassignment.ui.add_inspection_completed

import com.jay.shermassignment.utils.NetworkModule
import okhttp3.OkHttpClient

object CompletedInstance {
    val api:CompletedApi by lazy {
        NetworkModule.provideRetrofit(OkHttpClient())
            .create(CompletedApi::class.java)
    }
}