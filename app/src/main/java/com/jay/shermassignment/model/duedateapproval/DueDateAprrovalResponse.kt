package com.jay.shermassignment.model.duedateapproval

data class DueDateAprrovalResponse(
    val content: Content,
    val errorMap: List<Any>,
    val isSuccess: Boolean,
    val status: Int,
    val successMsg: String
)