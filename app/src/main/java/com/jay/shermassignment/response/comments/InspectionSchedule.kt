package com.jay.shermassignment.response.comments

data class InspectionSchedule(
    val active: Boolean,
    val assigner: Assigner,
    val completedDate: Any,
    val dueDate: String,
    val endDateInString: Any,
    val hasAssignerComment: Boolean,
    val hasDueDateExtends: Boolean,
    val id: Int,
    val inspectionId: String,
    val inspectionRescheduled: Boolean,
    val isCompleted: Boolean,
    val pastInspection: Boolean,
    val reschedule: Boolean,
    val responsiblePerson: ResponsiblePerson,
    val scheduledDateInString: Any,
    val version: Int,
    val workplaceInspection: WorkplaceInspection
)