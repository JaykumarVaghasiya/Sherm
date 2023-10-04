package com.jay.shermassignment.inspectionUI

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object InspectionDetailsInstance {
    val api: InspectionApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://staging.shermsoftware.com.au")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(InspectionApi::class.java)
    }
}