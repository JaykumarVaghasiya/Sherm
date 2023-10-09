package com.jay.shermassignment.ui.inspectionUI

import com.jay.shermassignment.utils.RetrofitInstance

object InspectionDetailsInstance {
       val api: InspectionApi by lazy {
           RetrofitInstance.api
            .create(InspectionApi::class.java)
    }
}