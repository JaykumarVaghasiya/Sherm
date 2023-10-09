package com.jay.shermassignment.ui.inspectionDetailsUI

import com.jay.shermassignment.utils.RetrofitInstance

object ShowInspectionDetailsInstance {
    val api: ShowInspectionDetailsAPI by lazy {
        RetrofitInstance.api
            .create(ShowInspectionDetailsAPI::class.java)
    }
}