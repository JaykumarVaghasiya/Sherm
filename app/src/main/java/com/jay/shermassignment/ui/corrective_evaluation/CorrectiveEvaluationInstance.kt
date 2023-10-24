package com.jay.shermassignment.ui.corrective_evaluation

import com.jay.shermassignment.utils.RetrofitInstance

object CorrectiveEvaluationInstance {

    val api: CorrectiveEvaluationAPI by lazy {
        RetrofitInstance.api.create(CorrectiveEvaluationAPI::class.java)
    }
}