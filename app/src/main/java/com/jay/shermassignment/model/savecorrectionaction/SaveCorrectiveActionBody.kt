package com.jay.shermassignment.model.savecorrectionaction

data class SaveCorrectiveActionBody(
    val action: String,
    val dueDate: String,
    val hazardId: Int,
    val hierarchyOfControl: HierarchyOfControl,
    val id: Int,
    val incidentId: Any,
    val inspectionScheduleId: Any,
    val issueId: Any,
    val responsibleId: Int,
    val reviewDate: String,
    val status: Int
)