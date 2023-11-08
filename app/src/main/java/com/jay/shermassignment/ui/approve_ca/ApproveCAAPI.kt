package com.jay.shermassignment.ui.approve_ca

import com.jay.shermassignment.model.approveca.ApproveCABody
import com.jay.shermassignment.model.approveca.ApproveCAResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header

interface ApproveCAAPI {

    @GET("/OHSClient/rest/v2/correctiveAction.do")
    suspend fun sendApproveCA(@Body approveCABody: ApproveCABody, @Header("Authorization") authToken: String
    ): Response<ApproveCAResponse>
}