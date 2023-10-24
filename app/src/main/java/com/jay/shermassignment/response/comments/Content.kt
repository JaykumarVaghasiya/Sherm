package com.jay.shermassignment.response.comments

data class Content(
    val attachments: Any,
    val date: Long,
    val id: Int,
    val inspectionComments: String,
    val inspectionSchedule: InspectionSchedule,
    val version: Int
)