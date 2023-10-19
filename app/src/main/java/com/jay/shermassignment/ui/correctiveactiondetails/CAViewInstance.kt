package com.jay.shermassignment.ui.correctiveactiondetails

import com.jay.shermassignment.utils.RetrofitInstance

object CAViewInstance {
    val api: CAViewAPI by lazy {
        RetrofitInstance.api
            .create(CAViewAPI::class.java)
    }
}