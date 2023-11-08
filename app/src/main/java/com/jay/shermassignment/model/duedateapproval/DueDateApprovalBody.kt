package com.jay.shermassignment.model.duedateapproval

data class DueDateApprovalBody(
    val action: String,
    val comment: String,
    val correctiveActionId: Int
)