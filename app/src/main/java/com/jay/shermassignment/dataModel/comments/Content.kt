package com.jay.shermassignment.dataModel.comments

data class Content(
    val attachments: Any,
    val date: Long,
    val id: Int,
    val inspectionComments: String,
    val inspectionSchedule: InspectionSchedule,
    val version: Int
)