package com.jay.shermassignment.dataModel.showInspection

data class ShowInspectionResponse(
    val category: String,
    val inspectionType: String,
    val site: String,
    val inspectionLocation: String,
    val responsiblePerson: String,
    val dueDate: String
)