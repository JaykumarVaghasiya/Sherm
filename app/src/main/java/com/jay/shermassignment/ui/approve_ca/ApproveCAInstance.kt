package com.jay.shermassignment.ui.approve_ca

import com.jay.shermassignment.utils.RetrofitInstance

object ApproveCAInstance {


        val api: ApproveCAAPI by lazy {
            RetrofitInstance.api
                .create(ApproveCAAPI::class.java)
        }

}