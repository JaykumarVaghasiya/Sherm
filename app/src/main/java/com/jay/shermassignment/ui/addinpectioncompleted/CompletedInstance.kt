package com.jay.shermassignment.ui.addinpectioncompleted

import com.jay.shermassignment.utils.RetrofitInstance

object CompletedInstance {
    val api:CompletedApi by lazy {
        RetrofitInstance.api
            .create(CompletedApi::class.java)
    }
}