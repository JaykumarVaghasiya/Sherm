package com.jay.shermassignment.ui.addinpectioncompleted

import com.jay.shermassignment.model.addinspectioncompletted.CompletedResponse
import com.jay.shermassignment.model.addinspectioncompletted.CompltedInspectionBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface CompletedApi {

    @POST("/OHSClient/rest/v2/inspection/completeScheduledInspection.do")
    suspend fun completedScheduledInspection(@Body completedInspectionBody: CompltedInspectionBody, @Header("Authorization") authToken: String): Response<CompletedResponse>
}