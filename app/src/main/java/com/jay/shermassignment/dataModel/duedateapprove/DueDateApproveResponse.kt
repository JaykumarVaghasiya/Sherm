package com.jay.shermassignment.dataModel.duedateapprove

data class DueDateApproveResponse(
    val content: Content,
    val errorMap: List<Any>,
    val isSuccess: Boolean,
    val status: Int,
    val successMsg: String
)