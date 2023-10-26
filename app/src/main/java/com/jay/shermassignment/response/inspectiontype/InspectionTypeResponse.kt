package com.jay.shermassignment.response.inspectiontype

data class InspectionTypeResponse(
    val content: List<Content>,
    val errorMap: List<Any>,
    val isSuccess: Boolean,
    val status: Int,
    val successMsg: String
)