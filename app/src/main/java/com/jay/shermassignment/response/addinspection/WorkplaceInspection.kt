package com.jay.shermassignment.response.addinspection

data class WorkplaceInspection(
    val description: Any,
    val formTemplate: FormTemplate,
    val id: Int,
    val inspectionCategoryMaster: InspectionCategoryMaster,
    val inspectionLocation: String,
    val inspectionType: InspectionType,
    val isActive: Boolean,
    val site: SiteXX,
    val version: Int
)