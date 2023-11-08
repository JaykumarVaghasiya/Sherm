package com.jay.shermassignment.model.duedateapproval

data class DueDateApprovalResponse(
    val content: Content,
    val errorMap: List<Any>,
    val isSuccess: Boolean,
    val status: Int,
    val successMsg: String
)