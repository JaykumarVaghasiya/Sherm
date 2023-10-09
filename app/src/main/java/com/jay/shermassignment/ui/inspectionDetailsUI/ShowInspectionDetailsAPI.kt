package com.jay.shermassignment.ui.inspectionDetailsUI

import com.jay.shermassignment.model.showInspection.ShowInspectionDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ShowInspectionDetailsAPI {
    @GET("/OHSClient/rest/v2/inspection/getInspectionDetail.do")
    suspend fun getShowInspectionDetails(@Query ("id") id : String ): Response<ShowInspectionDetails>

}