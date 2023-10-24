package com.jay.shermassignment.ui.inspectionUI


import com.jay.shermassignment.response.inspection.DeleteInspection
import com.jay.shermassignment.response.inspection.InspectionRef
import com.jay.shermassignment.response.inspection.InspectionResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface InspectionApi {

    @POST("/OHSClient/rest/v2/inspection/getAllInspections.do")
    suspend fun getInspectionDetails(@Body inspectionRef: InspectionRef, @Header("Authorization") authToken: String): Response<InspectionResponse>


    @DELETE("/OHSClient/rest/v2/inspection/delete.do")
    suspend fun deleteInspectionItem(@Query("id") id: Int, @Header("Authorization") authToken: String):Response<DeleteInspection>
}