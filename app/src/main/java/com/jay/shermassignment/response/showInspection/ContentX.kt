package com.jay.shermassignment.response.showInspection

data class ContentX(
    val description: Any,
    val formTemplate: FormTemplateX,
    val id: Int,
    val inspectionCategoryMaster: InspectionCategoryMasterX,
    val inspectionLocation: String,
    val inspectionType: InspectionTypeX,
    val isActive: Boolean,
    val site: SiteXX,
    val version: Int
)