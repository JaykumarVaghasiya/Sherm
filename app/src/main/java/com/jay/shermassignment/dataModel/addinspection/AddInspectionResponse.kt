package com.jay.shermassignment.dataModel.addinspection

data class AddInspectionResponse(
    val content: Content,
    val errorMap: List<Any>,
    val isSuccess: Boolean,
    val status: Int,
    val successMsg: String
)