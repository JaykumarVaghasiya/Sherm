package com.jay.shermassignment.dataModel.inspection

data class InspectionResponse(
    val content: Content,
    val errorMap: List<Any>,
    val isSuccess: Boolean,
    val status: Int,
    val successMsg: String
)