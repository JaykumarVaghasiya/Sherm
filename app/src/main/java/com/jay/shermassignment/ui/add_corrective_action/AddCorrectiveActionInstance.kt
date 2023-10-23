package com.jay.shermassignment.ui.add_corrective_action

import com.jay.shermassignment.utils.RetrofitInstance

object AddCorrectiveActionInstance {
    val api: AddCorrectiveActionAPI by lazy {
        RetrofitInstance.api.create(AddCorrectiveActionAPI::class.java)
    }
}