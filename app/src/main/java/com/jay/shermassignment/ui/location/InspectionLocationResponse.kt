package com.jay.shermassignment.ui.location

data class InspectionLocationResponse(
    val content: List<Content>,
    val errorMap: List<Any>,
    val isSuccess: Boolean,
    val status: Int,
    val successMsg: String
)