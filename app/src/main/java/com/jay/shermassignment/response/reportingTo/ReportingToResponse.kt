package com.jay.shermassignment.response.reportingTo

data class ReportingToResponse(
    val content: List<Content>,
    val errorMap: List<Any>,
    val isSuccess: Boolean,
    val status: Int,
    val successMsg: String
)