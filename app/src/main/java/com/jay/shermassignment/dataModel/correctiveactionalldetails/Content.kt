package com.jay.shermassignment.dataModel.correctiveactionalldetails

data class Content(
    val action: String,
    val assignerId: Int,
    val assignerName: String,
    val beforeReviewDateReminderSent: Boolean,
    val createdOn: String,
    val dueDate: String,
    val employeeDetails: String,
    val hasDueDateExtension: Boolean,
    val hazardId: Any,
    val hierarchyOfControl: HierarchyOfControl,
    val id: Int,
    val incidentId: Any,
    val inspectionScheduleId: Any,
    val isAssignee: Boolean,
    val isAssigner: Boolean,
    val isSecondaryAdmin: Boolean,
    val isSuperAdmin: Boolean,
    val issueId: Any,
    val lastStatusChangeDate: String,
    val outOfDueDateReminderSent: Boolean,
    val parentId: Int,
    val parentType: String,
    val responsibleId: Int,
    val responsiblePersonName: String,
    val reviewDate: String,
    val reviewDueDateReminderSent: Boolean,
    val siteAdminForParentObject: Boolean,
    val status: Int
)