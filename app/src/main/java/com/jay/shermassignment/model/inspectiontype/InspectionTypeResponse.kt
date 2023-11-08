package com.jay.shermassignment.model.inspectiontype

data class InspectionTypeResponse(
    val content: List<Content>,
    val errorMap: List<Any>,
    val isSuccess: Boolean,
    val status: Int,
    val successMsg: String
)