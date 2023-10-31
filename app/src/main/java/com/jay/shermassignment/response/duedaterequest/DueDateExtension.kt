package com.jay.shermassignment.response.duedaterequest

data class DueDateExtension(
    val approverComment: String,
    val id: Int? =null,
    val prefferedDate: String,
    val requestorComment: String,
    val status: String
)