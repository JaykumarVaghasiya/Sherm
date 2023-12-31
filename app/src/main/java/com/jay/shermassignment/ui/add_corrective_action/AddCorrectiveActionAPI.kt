package com.jay.shermassignment.ui.add_corrective_action

import com.jay.shermassignment.response.addcorrectiveaction.AddCorrectiveActionResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AddCorrectiveActionAPI {

    @POST("/OHSClient/rest/v2/saveCorrectiveAction.do")
    suspend fun addCorrectiveAction(@Body addCorrectiveActionBody: AddCorrectiveActionBody, @Header("Authorization") authToken: String): Response<AddCorrectiveActionResponse>
}