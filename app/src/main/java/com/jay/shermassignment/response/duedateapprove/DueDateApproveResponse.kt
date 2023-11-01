package com.jay.shermassignment.response.duedateapprove

data class DueDateApproveResponse(
    val content: Content,
    val errorMap: List<Any>,
    val isSuccess: Boolean,
    val status: Int,
    val successMsg: String
)