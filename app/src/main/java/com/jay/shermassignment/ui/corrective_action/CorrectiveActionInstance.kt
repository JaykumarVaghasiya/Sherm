package com.jay.shermassignment.ui.corrective_action

import com.jay.shermassignment.utils.NetworkModule
import okhttp3.OkHttpClient

object CorrectiveActionInstance {
    val api: CorrectiveActionAPI by lazy {
        NetworkModule.provideRetrofit(OkHttpClient())
            .create(CorrectiveActionAPI::class.java)
    }
}