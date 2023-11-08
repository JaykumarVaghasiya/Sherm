package com.jay.shermassignment.ui.inspectionUI

import com.jay.shermassignment.utils.NetworkModule
import okhttp3.OkHttpClient

object InspectionDetailsInstance {
       val api: InspectionApi by lazy {
           NetworkModule.provideRetrofit(OkHttpClient())
            .create(InspectionApi::class.java)
    }
}