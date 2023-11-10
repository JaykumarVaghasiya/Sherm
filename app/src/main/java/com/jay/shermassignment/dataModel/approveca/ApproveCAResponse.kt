package com.jay.shermassignment.dataModel.approveca

data class ApproveCAResponse(
    val content: Content,
    val errorMap: List<Any>,
    val isSuccess: Boolean,
    val status: Int,
    val successMsg: String
)