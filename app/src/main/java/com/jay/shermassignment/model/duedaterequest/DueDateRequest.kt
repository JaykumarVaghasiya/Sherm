package com.jay.shermassignment.model.duedaterequest

data class DueDateRequest(
    val content: Content,
    val errorMap: List<Any>,
    val isSuccess: Boolean,
    val status: Int,
    val successMsg: String
)