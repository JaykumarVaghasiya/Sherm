package com.jay.shermassignment.ui.dueDate

import com.jay.shermassignment.utils.NetworkModule
import okhttp3.OkHttpClient

object DueDateInstance {
    val api: DueDateAPI by lazy {
        NetworkModule.provideRetrofit(OkHttpClient())
            .create(DueDateAPI::class.java)
    }
}