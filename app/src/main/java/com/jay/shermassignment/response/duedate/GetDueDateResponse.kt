package com.jay.shermassignment.response.duedate

data class GetDueDateResponse(
    val content: Content,
    val errorMap: List<Any>,
    val isSuccess: Boolean,
    val status: Int,
    val successMsg: String
)