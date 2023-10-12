package com.jay.shermassignment.model.addinspection

data class Content(
    val active: Boolean,
    val assigner: Assigner,
    val completedDate: Any,
    val dueDate: Long,
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