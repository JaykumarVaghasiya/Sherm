package com.jay.shermassignment.model.correctiveaction

data class CorrectiveActionResponseX(
    val content: Content,
    val errorMap: List<Any>,
    val isSuccess: Boolean,
    val status: Int,
    val successMsg: String
)