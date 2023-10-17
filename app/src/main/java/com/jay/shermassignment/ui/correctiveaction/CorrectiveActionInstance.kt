package com.jay.shermassignment.ui.correctiveaction

import com.jay.shermassignment.utils.RetrofitInstance

object CorrectiveActionInstance {
    val api: CorrectiveActionAPI by lazy {
        RetrofitInstance.api
            .create(CorrectiveActionAPI::class.java)
    }
}