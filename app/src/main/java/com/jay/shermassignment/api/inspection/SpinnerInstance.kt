package com.jay.shermassignment.api.inspection

import com.jay.shermassignment.utils.RetrofitInstance

object SpinnerInstance {

    val api: SpinnerAPI by lazy {
        RetrofitInstance.api.create(
            SpinnerAPI::class.java
        )
    }
}