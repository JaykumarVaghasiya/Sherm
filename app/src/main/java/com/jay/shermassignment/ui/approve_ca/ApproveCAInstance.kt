package com.jay.shermassignment.ui.approve_ca

import com.jay.shermassignment.utils.NetworkModule
import okhttp3.OkHttpClient

object ApproveCAInstance {


        val api: ApproveCAAPI by lazy {
            NetworkModule.provideRetrofit(OkHttpClient())
                .create(ApproveCAAPI::class.java)
        }

}