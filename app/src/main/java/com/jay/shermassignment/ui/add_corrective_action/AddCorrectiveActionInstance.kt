package com.jay.shermassignment.ui.add_corrective_action

import com.jay.shermassignment.utils.NetworkModule
import okhttp3.OkHttpClient

object AddCorrectiveActionInstance {
    val api: AddCorrectiveActionAPI by lazy {
        NetworkModule.provideRetrofit(OkHttpClient()).create(AddCorrectiveActionAPI::class.java)
    }
}