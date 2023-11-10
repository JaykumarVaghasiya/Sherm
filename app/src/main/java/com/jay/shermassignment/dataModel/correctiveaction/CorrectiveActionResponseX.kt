package com.jay.shermassignment.dataModel.correctiveaction

data class CorrectiveActionResponseX(
    val content: Content,
    val errorMap: List<Any>,
    val isSuccess: Boolean,
    val status: Int,
    val successMsg: String
)