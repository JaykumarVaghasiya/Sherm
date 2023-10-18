package com.jay.shermassignment.ui.dueDate

import com.jay.shermassignment.ui.inspectionUI.InspectionApi
import com.jay.shermassignment.utils.RetrofitInstance

object DueDateInstance {
    val api: DueDateAPI by lazy {
        RetrofitInstance.api
            .create(DueDateAPI::class.java)
    }
}