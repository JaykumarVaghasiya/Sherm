package com.jay.shermassignment.api.inspection

import com.jay.shermassignment.model.inspectiontype.InspectionTypeResponse
import com.jay.shermassignment.model.reportingTo.ReportingToResponse
import com.jay.shermassignment.model.responsibleperson.ResponsiblePersonResponse
import com.jay.shermassignment.model.location.InspectionLocationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SpinnerAPI {
    @GET("/OHSClient/rest/v2/inspection/getInspectionTypeFromCategory.do")
    suspend fun getInspectionTypeFromCategory(
        @Query("categoryId") id: Int,
        @Header("Authorization") authToken: String
    ): Response<InspectionTypeResponse>

    @GET("OHSClient/rest/v2/inspection/getInspectionLocationFromCategoryAndType.do")
    suspend fun getLocationFromCatInsTypeSite(
        @Query("categoryId") catId: Int,
        @Query("siteId") siteId: Int,
        @Query("inspectionTypeId") inspectionTypeId: Int,
        @Header("Authorization") authToken: String
    ): Response<InspectionLocationResponse>

    @GET("/OHSClient/rest/v2/inspection/getAllSiteInspectionResponsiblePerson.do")
    suspend fun getResponsiblePersonBySite(
        @Query("siteId") siteId: Int,
        @Header("Authorization") authToken: String
    ): Response<ResponsiblePersonResponse>

    @GET("/OHSClient/rest/v2/getAllReportedBy.do")
    suspend fun getAlLResponsiblePerson(
        @Header("Authorization") authToken: String
    ): Response<ReportingToResponse>
}