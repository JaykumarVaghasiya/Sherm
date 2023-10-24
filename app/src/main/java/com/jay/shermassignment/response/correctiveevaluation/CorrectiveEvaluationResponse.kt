package com.jay.shermassignment.response.correctiveevaluation

data class CorrectiveEvaluationResponse(
    val content: Content,
    val errorMap: List<Any>,
    val isSuccess: Boolean,
    val status: Int,
    val successMsg: String
)