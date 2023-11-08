package com.jay.shermassignment.model.addInspectionData

data class AddInspectionRef(
    val id : Int?= null,
    val assignerId: Int,
    val dueDate: String,
    val inspectionLocation: String,
    val inspectionType: InspectionType,
    val reschedule: Boolean,
    val responsiblePerson: ResponsiblePerson,
    val workplaceInspection: WorkplaceInspection
)