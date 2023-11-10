package com.jay.shermassignment.dataModel.correctiveactionalldetails

data class CorrectiveActionDetailsResponse(
    val content: Content,
    val errorMap: List<Any>,
    val isSuccess: Boolean,
    val status: Int,
    val successMsg: String
)