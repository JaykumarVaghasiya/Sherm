package com.jay.shermassignment.inspectionUI


import com.jay.shermassignment.model.inspection.InspectionRef
import com.jay.shermassignment.model.inspection.InspectionResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST

interface InspectionApi {
    @POST("/OHSClient/rest/v2/inspection/getAllInspections.do")
    suspend fun getInspectionDetails(@Body inspectionRef: InspectionRef): Response<InspectionResponse>

    @DELETE("/OHSClient/rest/v2/inspection/delete.do")
    suspend fun deleteInspectionItem(@Body inspectionRef: InspectionRef):Response<InspectionResponse>
}