package com.jay.shermassignment.dataModel.showInspection

data class SiteResponse(
    val content: List<ContentX>,
    val errorMap: List<Any>,
    val isSuccess: Boolean,
    val status: Int,
    val successMsg: String
)