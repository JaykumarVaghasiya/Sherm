package com.jay.shermassignment.model.responsibleperson

data class ResponsiblePersonResponse(
    val content: List<Content>,
    val errorMap: List<Any>,
    val isSuccess: Boolean,
    val status: Int,
    val successMsg: String
)