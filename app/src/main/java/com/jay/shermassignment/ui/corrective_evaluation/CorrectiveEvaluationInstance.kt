package com.jay.shermassignment.ui.corrective_evaluation

import com.jay.shermassignment.utils.NetworkModule
import okhttp3.OkHttpClient

object CorrectiveEvaluationInstance {

    val api: CorrectiveEvaluationAPI by lazy {
        NetworkModule.provideRetrofit(OkHttpClient()).create(CorrectiveEvaluationAPI::class.java)
    }
}