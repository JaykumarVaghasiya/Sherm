package com.jay.shermassignment.response.duedateapproval

data class DueDateApprovalResponse(
    val content: Content,
    val errorMap: List<Any>,
    val isSuccess: Boolean,
    val status: Int,
    val successMsg: String
)