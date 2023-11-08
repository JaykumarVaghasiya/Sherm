package com.jay.shermassignment.ui.corrective_action_details

import com.jay.shermassignment.utils.NetworkModule
import okhttp3.OkHttpClient

object CAViewInstance {
    val api: CAViewAPI by lazy {
        NetworkModule.provideRetrofit(OkHttpClient())
            .create(CAViewAPI::class.java)
    }
}