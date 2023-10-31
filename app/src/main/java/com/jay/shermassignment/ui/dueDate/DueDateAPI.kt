package com.jay.shermassignment.ui.dueDate

import com.jay.shermassignment.response.duedateapproval.DueDateApprovalBody
import com.jay.shermassignment.response.duedateapproval.DueDateAprrovalResponse
import com.jay.shermassignment.response.duedaterequest.DueDateBody
import com.jay.shermassignment.response.duedaterequest.DueDateRequestReponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface DueDateAPI {
    @POST("/OHSClient/rest/v2/extendDueDate.do")
    suspend fun getDueDateRequest(
        @Query("operation") operation: String,
        @Body dueDateRequest: DueDateBody,
        @Header("Authorization") authToken: String
    ): Response<DueDateRequestReponse>

    @POST("/OHSClient/rest/v2/extendDueDate.do")
    suspend fun getDueDateApproval(
        @Body dueDateApprovalBody: DueDateApprovalBody,
        @Query("correctiveActionId") correctiveActionId: Int,
        @Header("Authorization") authToken: String
    ): Response<DueDateAprrovalResponse>
}