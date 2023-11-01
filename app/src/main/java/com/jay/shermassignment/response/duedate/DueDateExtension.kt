package com.jay.shermassignment.response.duedate

data class DueDateExtension(
    val approverComment: Any,
    val id: Int?,
    val prefferedDate: String,
    val requestorComment: String,
    val status: String,
    val version: Int
)