package com.jay.shermassignment.ui.commentUI

import com.jay.shermassignment.dataModel.comments.CommentBody
import com.jay.shermassignment.dataModel.comments.CommentResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface CommentApi {

    @POST("/OHSClient/rest/v2/inspection/addCommentsForInspection.do")
    suspend fun addCommentsForInspection(@Body commentBody: CommentBody,  @Header("Authorization") authToken: String): Response<CommentResponse>
}