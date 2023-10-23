package com.jay.shermassignment.ui.corrective_action_details

import com.jay.shermassignment.utils.RetrofitInstance

object CAViewInstance {
    val api: CAViewAPI by lazy {
        RetrofitInstance.api
            .create(CAViewAPI::class.java)
    }
}