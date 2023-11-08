package com.jay.shermassignment.model.duedateapprove

data class DueDateExtensionX(
    val approverComment: String,
    val id: Int,
    val prefferedDate: Long,
    val requestorComment: String,
    val status: String,
    val version: Int
)