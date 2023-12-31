package com.jay.shermassignment.response.inspection

data class Row(
    val action: Any,
    val approvedDocument: ApprovedDocument,
    val caType: Any,
    val completedDate: Any,
    val dueDate: String,
    val electronicWebForm: Boolean,
    val existingControl: Boolean,
    val hasDueDateExtension: Boolean,
    val hasManageOrViewRights: Boolean,
    val hasManageRights: Boolean,
    val id: Int,
    val inspectionId: String,
    val inspectionLocation: String,
    val inspectionType: String,
    val isAssignee: Boolean,
    val isAssigneeForParentObject: Boolean,
    val isAssigner: Boolean,
    val isBoth: Boolean,
    val isCorrectiveActionAssigned: Boolean,
    val isInspectionCompleted: Boolean,
    val isReportedBy: Boolean,
    val isReportedTo: Boolean,
    val isSecondaryAdmin: Boolean,
    val isSuperAdmin: Boolean,
    val jobTaskName: Any,
    val reported: Any,
    val respersonId: Int,
    val responsible: String,
    val secondaryAdmin: Boolean,
    val siteAdminForParentObject: Boolean,
    val siteManagerForInspection: Boolean,
    val status: String,
    val statusId: Any,
    val superAdmin: Boolean,
    val swmsNo: Any,
    val workPlaceArea: Any
)