package com.jay.shermassignment.ui.corrective_evaluation

import com.jay.shermassignment.response.correctiveevaluation.CorrectiveEvaluationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface CorrectiveEvaluationAPI {

    @POST("/OHSClient/rest/v2/addEvaluation.do")
    suspend fun addEvaluation(
        @Body correctiveEvaluationBody: CorrectiveEvaluationBody,
        @Header("Authorization") authToken: String
    ): Response<CorrectiveEvaluationResponse>
}