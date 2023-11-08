package com.jay.shermassignment.model.approveca

data class ApproveCABody(
    val action: String,
    val comment: String,
    val correctiveActionId: Int
)