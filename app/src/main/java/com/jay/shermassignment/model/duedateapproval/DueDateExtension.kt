package com.jay.shermassignment.model.duedateapproval

data class DueDateExtension(
    val approverComment: String,
    val id: Int,
    val prefferedDate: String,
    val requestorComment: String,
    val status: String
)