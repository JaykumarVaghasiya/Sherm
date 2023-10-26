package com.jay.shermassignment.response.responsibleperson

data class ResponsiblePersonResponse(
    val content: List<Content>,
    val errorMap: List<Any>,
    val isSuccess: Boolean,
    val status: Int,
    val successMsg: String
)