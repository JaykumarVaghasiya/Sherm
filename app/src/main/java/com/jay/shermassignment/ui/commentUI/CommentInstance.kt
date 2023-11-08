package com.jay.shermassignment.ui.commentUI

import com.jay.shermassignment.utils.NetworkModule
import okhttp3.OkHttpClient

object CommentInstance {
    val api: CommentApi by lazy {
        NetworkModule.provideRetrofit(OkHttpClient())
            .create(CommentApi::class.java)
    }
}