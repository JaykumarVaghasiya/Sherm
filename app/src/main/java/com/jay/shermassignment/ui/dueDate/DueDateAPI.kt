package com.jay.shermassignment.ui.dueDate

import com.jay.shermassignment.dataModel.approveca.ApproveCABody
import com.jay.shermassignment.dataModel.approveca.ApproveCAResponse
import com.jay.shermassignment.dataModel.extenddate.ExtendDateBody
import com.jay.shermassignment.dataModel.extenddate.ExtendDateResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface DueDateAPI {
    @POST("/OHSClient/rest/v2/extendDueDate.do")
    suspend fun getDueDateRequest(
        @Query("operation") operation: String,
        @Body dueDateRequest: ExtendDateBody,
        @Header("Authorization") authToken: String
    ): Response<ExtendDateResponse>

    @POST("/OHSClient/rest/v2/correctiveAction.do")
    suspend fun getDueDateApproval(
        @Body dueDateApprovalBody: ApproveCABody,
        @Header("Authorization") authToken: String
    ): Response<ApproveCAResponse>
}