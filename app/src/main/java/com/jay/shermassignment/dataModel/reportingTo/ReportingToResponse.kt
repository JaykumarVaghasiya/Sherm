package com.jay.shermassignment.dataModel.reportingTo

data class ReportingToResponse(
    val content: List<Content>,
    val errorMap: List<Any>,
    val isSuccess: Boolean,
    val status: Int,
    val successMsg: String
)