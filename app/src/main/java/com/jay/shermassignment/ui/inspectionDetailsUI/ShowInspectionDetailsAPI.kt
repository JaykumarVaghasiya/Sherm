package com.jay.shermassignment.ui.inspectionDetailsUI

import com.jay.shermassignment.model.addInspectionData.addInspectionRef
import com.jay.shermassignment.model.addinspection.AddInspectionResponse
import com.jay.shermassignment.model.showInspection.ShowInspectionDetails
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ShowInspectionDetailsAPI {
    @GET("/OHSClient/rest/v2/inspection/getInspectionDetail.do")
    suspend fun getShowInspectionDetails(@Query ("id") id : Int , @Header("Authorization") authToken: String): Response<ShowInspectionDetails>

    @GET("/OHSClient/rest/v2/inspection/saveUpdateInspection.do")
    suspend fun getAddInspectionData(@Body addInspectionBody:addInspectionRef, @Header("Authorization")authToken: String):Response<AddInspectionResponse>

}