package com.jay.shermassignment.dataModel.comments

data class CommentResponse(
    val content: Content,
    val errorMap: List<Any>,
    val isSuccess: Boolean,
    val status: Int,
    val successMsg: String
)