package com.jay.shermassignment.response.addinspection

data class AddInspectionBody(
    val assignerId: Int,
    val dueDate: String,
    val id: Int? = null,
    val inspectionLocation: String,
    val inspectionType: InspectionType,
    val reschedule: Boolean,
    val responsiblePerson: ResponsiblePerson,
    val workplaceInspection: WorkplaceInspection
)