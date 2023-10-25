package com.jay.shermassignment.response.addInspectionData

data class addInspectionRef(
    val assignerId: Int,
    val dueDate: String,
    val inspectionLocation: String,
    val inspectionType: InspectionType,
    val reschedule: Boolean,
    val responsiblePerson: ResponsiblePerson,
    val workplaceInspection: WorkplaceInspection
)