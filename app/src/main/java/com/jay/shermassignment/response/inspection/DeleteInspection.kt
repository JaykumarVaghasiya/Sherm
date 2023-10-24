package com.jay.shermassignment.response.inspection

data class DeleteInspection(
    val content: String,
    val errorMap: List<Any>,
    val isSuccess: Boolean,
    val status: Int,
    val successMsg: String
)