package com.jay.shermassignment.model.showInspection

data class SiteResponse(
    val content: List<ContentX>,
    val errorMap: List<Any>,
    val isSuccess: Boolean,
    val status: Int,
    val successMsg: String
)