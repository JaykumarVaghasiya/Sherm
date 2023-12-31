package com.jay.shermassignment.response.extenddate

data class ExtendDateResponse(
    val content: Content,
    val errorMap: List<Any>,
    val isSuccess: Boolean,
    val status: Int,
    val successMsg: String
)