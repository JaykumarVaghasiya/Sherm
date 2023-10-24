package com.jay.shermassignment.response.addcorrectiveaction

data class AddCorrectiveActionResponse(
    val content: Content,
    val errorMap: List<Any>,
    val isSuccess: Boolean,
    val status: Int,
    val successMsg: String
)