package com.jay.shermassignment.dataModel.approveca

data class ApproveCABody(
    val action: String,
    val comment: String,
    val correctiveActionId: Int
)