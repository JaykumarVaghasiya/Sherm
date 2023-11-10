package com.jay.shermassignment.ui.corrective_action_details

import com.jay.shermassignment.dataModel.correctiveactionalldetails.CorrectiveActionDetailsResponse
import com.jay.shermassignment.dataModel.duedate.GetDueDateResponse
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

    @GET("/OHSClient/rest/v2/getExtendDueDateRequest.do")
    suspend fun checkDueDateExtend(
        @Query("correctiveActionId") correctiveActionId: Int,
        @Header("Authorization") authToken: String
    ): Response<GetDueDateResponse>
}