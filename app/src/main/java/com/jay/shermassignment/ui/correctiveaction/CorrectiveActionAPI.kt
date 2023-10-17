package com.jay.shermassignment.ui.correctiveaction

import com.jay.shermassignment.model.correctiveaction.CorrectiveActionData
import com.jay.shermassignment.model.correctiveaction.CorrectiveActionResponseX
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface CorrectiveActionAPI {

    @POST("/OHSClient/rest/v2/getAllCorrectiveActions.do")
    suspend fun getAllCorrectiveAction(
        @Body correctiveActionBody: CorrectiveActionData,
        @Header("Authorization") authToken: String
    ): Response<CorrectiveActionResponseX>
}