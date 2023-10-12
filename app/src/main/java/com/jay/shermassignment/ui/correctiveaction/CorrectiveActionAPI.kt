package com.jay.shermassignment.ui.correctiveaction

import com.jay.shermassignment.model.correctiveaction.CorrectiveActionBody
import com.jay.shermassignment.model.correctiveactionalldetails.CorrectiveActionDetailsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

interface CorrectiveActionAPI {

    @GET("/OHSClient/rest/v2/getAllCorrectiveActions.do")
    suspend fun getAllCorrectiveAction(@Body correctiveActionBody: CorrectiveActionBody):Response<CorrectiveActionDetailsResponse>
}