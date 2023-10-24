package com.jay.shermassignment.response.addinspection

data class AddInspectionResponse(
    val content: Content,
    val errorMap: List<Any>,
    val isSuccess: Boolean,
    val status: Int,
    val successMsg: String
)