package com.jay.shermassignment.response.duedaterequest

data class DueDateExtensionX(
    val approverComment: Any,
    val id: Int,
    val prefferedDate: Long,
    val requestorComment: String,
    val status: String,
    val version: Int
)