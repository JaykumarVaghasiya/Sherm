package com.jay.shermassignment.dataModel.location

data class Content(
    val description: Any,
    val formTemplate: FormTemplate,
    val id: Int,
    val inspectionCategoryMaster: InspectionCategoryMaster,
    val inspectionLocation: String,
    val inspectionType: InspectionType,
    val isActive: Boolean,
    val site: Site,
    val version: Int
)