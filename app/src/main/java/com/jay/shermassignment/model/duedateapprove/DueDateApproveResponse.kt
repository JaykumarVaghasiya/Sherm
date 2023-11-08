package com.jay.shermassignment.model.duedateapprove

data class DueDateApproveResponse(
    val content: Content,
    val errorMap: List<Any>,
    val isSuccess: Boolean,
    val status: Int,
    val successMsg: String
)