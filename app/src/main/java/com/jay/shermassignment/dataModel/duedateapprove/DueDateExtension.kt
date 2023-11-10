package com.jay.shermassignment.dataModel.duedateapprove

data class DueDateExtension(
    val approverComment: String,
    val id: Int,
    val prefferedDate: String,
    val requestorComment: String,
    val status: String
)