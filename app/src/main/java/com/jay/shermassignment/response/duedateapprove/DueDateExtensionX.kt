package com.jay.shermassignment.response.duedateapprove

data class DueDateExtensionX(
    val approverComment: String,
    val id: Int,
    val prefferedDate: Long,
    val requestorComment: String,
    val status: String,
    val version: Int
)