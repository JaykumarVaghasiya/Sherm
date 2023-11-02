package com.jay.shermassignment.response.extenddate

data class DueDateExtensionX(
    val approverComment: String,
    val id: Int,
    val prefferedDate: String,
    val requestorComment: String,
    val status: String,
    val version: Int
)