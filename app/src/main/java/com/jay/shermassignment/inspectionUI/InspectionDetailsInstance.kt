package com.jay.shermassignment.inspectionUI

import com.jay.shermassignment.RetrofitInstance

object InspectionDetailsInstance {
       val api: InspectionApi by lazy {
           RetrofitInstance.api
            .create(InspectionApi::class.java)
    }
}