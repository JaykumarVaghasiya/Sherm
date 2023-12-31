package com.jay.shermassignment.response.showInspection

data class WorkplaceInspection(
    val description: Any,
    val formTemplate: FormTemplate,
    val id: Int,
    val inspectionCategoryMaster: InspectionCategoryMaster,
    val inspectionLocation: String,
    val inspectionType: InspectionType,
    val isActive: Boolean,
    val site: SiteX,
    val version: Int
)