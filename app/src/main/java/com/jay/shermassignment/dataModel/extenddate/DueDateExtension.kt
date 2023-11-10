package com.jay.shermassignment.dataModel.extenddate

data class DueDateExtension(
    val approverComment: String,
    val id: Int?,
    val prefferedDate: String,
    val requestorComment: String,
    val status: String
)