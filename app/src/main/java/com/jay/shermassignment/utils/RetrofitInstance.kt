package com.jay.shermassignment.utils

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {


    private val interceptor= HttpLoggingInterceptor().apply {
        this.level= HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient? = OkHttpClient.Builder().apply {
        this.addInterceptor(interceptor)
            .callTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)


    }.build()


    val api: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://staging.shermsoftware.com.au")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client!!)
            .build()
    }
}