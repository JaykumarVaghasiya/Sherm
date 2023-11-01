package com.jay.shermassignment.response.duedateapproval

data class DueDateApprovalBody(
    val action: String,
    val comment: String,
    val correctiveActionId: Int
)