package com.jay.shermassignment.model.showInspection

data class Content(
    val asignee: Boolean,
    val asigner: Boolean,
    val assignerId: Any,
    val dueDate: String,
    val id: Any,
    val inspectionCategoryMaster: InspectionCategoryMaster,
    val inspectionId: String,
    val inspectionLocation: String,
    val inspectionType: InspectionType,
    val reschedule: Boolean,
    val responsiblePerson: ResponsiblePerson,
    val workplaceInspection: WorkplaceInspection
)