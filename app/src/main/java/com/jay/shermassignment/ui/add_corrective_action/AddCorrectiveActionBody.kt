package com.jay.shermassignment.ui.add_corrective_action

data class AddCorrectiveActionBody(
    val action: String,
    val dueDate: String,
    val hazardId: Int,
    val hierarchyOfControl: HierarchyOfControl,
    val incidentId: Any?,
    val inspectionScheduleId: Any?,
    val issueId: Any?,
    val responsibleId: Int?,
    val reviewDate: String,
    val status: Int = 1
)