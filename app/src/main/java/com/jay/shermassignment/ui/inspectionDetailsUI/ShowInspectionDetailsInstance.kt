package com.jay.shermassignment.ui.inspectionDetailsUI

import com.jay.shermassignment.utils.NetworkModule
import okhttp3.OkHttpClient

object ShowInspectionDetailsInstance {
    val api: ShowInspectionDetailsAPI by lazy {
        NetworkModule.provideRetrofit(OkHttpClient())
            .create(ShowInspectionDetailsAPI::class.java)
    }
}