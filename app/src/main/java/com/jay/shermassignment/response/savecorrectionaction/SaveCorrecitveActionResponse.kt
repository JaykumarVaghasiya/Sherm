package com.jay.shermassignment.response.savecorrectionaction

data class SaveCorrecitveActionResponse(
    val content: Content,
    val errorMap: List<Any>,
    val isSuccess: Boolean,
    val status: Int,
    val successMsg: String
)