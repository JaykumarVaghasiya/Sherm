package com.jay.shermassignment.dataModel.addinspectioncompletted

data class Content(
    val active: Boolean,
    val assigner: Assigner,
    val completedDate: Long,
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