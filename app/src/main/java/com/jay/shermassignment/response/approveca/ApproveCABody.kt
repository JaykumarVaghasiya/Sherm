package com.jay.shermassignment.response.approveca

data class ApproveCABody(
    val action: String,
    val comment: String,
    val correctiveActionId: Int
)