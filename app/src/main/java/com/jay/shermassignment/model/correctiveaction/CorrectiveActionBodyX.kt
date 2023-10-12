package com.jay.shermassignment.model.correctiveaction

data class CorrectiveActionBodyX(
    val content: Content,
    val errorMap: List<Any>,
    val isSuccess: Boolean,
    val status: Int,
    val successMsg: String
)