package com.jay.shermassignment.dataModel.showInspection

data class ShowInspectionDetails(
    val content: Content,
    val errorMap: List<Any>,
    val isSuccess: Boolean,
    val status: Int,
    val successMsg: String
)