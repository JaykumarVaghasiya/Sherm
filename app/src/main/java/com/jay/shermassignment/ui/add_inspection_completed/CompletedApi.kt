package com.jay.shermassignment.ui.add_inspection_completed

import com.jay.shermassignment.response.addinspectioncompletted.CompletedResponse
import com.jay.shermassignment.response.addinspectioncompletted.CompletedInspectionBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface CompletedApi {

    @POST("/OHSClient/rest/v2/inspection/completeScheduledInspection.do")
    suspend fun completedScheduledInspection(@Body completedInspectionBody: CompletedInspectionBody, @Header("Authorization") authToken: String): Response<CompletedResponse>
}