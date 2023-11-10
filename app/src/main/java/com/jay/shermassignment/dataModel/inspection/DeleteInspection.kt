package com.jay.shermassignment.dataModel.inspection

data class DeleteInspection(
    val content: String,
    val errorMap: List<Any>,
    val isSuccess: Boolean,
    val status: Int,
    val successMsg: String
)