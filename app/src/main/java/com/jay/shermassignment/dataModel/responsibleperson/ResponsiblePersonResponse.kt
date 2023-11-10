package com.jay.shermassignment.dataModel.responsibleperson

data class ResponsiblePersonResponse(
    val content: List<Content>,
    val errorMap: List<Any>,
    val isSuccess: Boolean,
    val status: Int,
    val successMsg: String
)