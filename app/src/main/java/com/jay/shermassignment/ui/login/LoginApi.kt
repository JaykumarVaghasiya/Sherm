package com.jay.shermassignment.ui.login

import com.jay.shermassignment.dataModel.user.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("/OHSClient/rest/v2/getUser.do")
    suspend fun getUserDetails(@Body userDetails: UserDetails):Response<UserResponse>
}