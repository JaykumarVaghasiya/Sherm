package com.jay.shermassignment.model.showInspection

data class ShowInspectionDetails(
    val content: Content,
    val errorMap: List<Any>,
    val isSuccess: Boolean,
    val status: Int,
    val successMsg: String
)