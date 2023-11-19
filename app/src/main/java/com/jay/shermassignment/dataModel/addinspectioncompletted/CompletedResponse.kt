package com.jay.shermassignment.dataModel.addinspectioncompletted

data class CompletedResponse(
    val content: Content,
    val errorMap: List<Any>,
    val isSuccess: Boolean,
    val status: Int,
    val successMsg: String
)