package com.jay.shermassignment.ui.correctiveactiondetails

import com.jay.shermassignment.model.correctiveactionalldetails.CorrectiveActionDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CAViewAPI {
    @GET("/OHSClient/rest/v2/getCorrectiveAction.do")
    suspend fun getAllCAViewDetails(
        @Query("id") correctiveActionId: Int,
        @Header("Authorization") authToken: String
    ): Response<CorrectiveActionDetailsResponse>
}