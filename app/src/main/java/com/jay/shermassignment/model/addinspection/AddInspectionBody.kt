package com.jay.shermassignment.model.addinspection

data class AddInspectionBody(
    val assignerId: Int,
    val dueDate: String,
    val id: Any,
    val inspectionLocation: String,
    val inspectionType: InspectionType,
    val reschedule: Boolean,
    val responsiblePerson: ResponsiblePerson,
    val workplaceInspection: WorkplaceInspection
)