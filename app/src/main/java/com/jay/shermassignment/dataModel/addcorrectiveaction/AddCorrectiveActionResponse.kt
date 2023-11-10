package com.jay.shermassignment.dataModel.addcorrectiveaction

data class AddCorrectiveActionResponse(
    val content: Content,
    val errorMap: List<Any>,
    val isSuccess: Boolean,
    val status: Int,
    val successMsg: String
)