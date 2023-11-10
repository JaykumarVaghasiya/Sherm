package com.jay.shermassignment.dataModel.addinspectioncompletted

data class WorkplaceInspection(
    val description: String,
    val formTemplate: FormTemplate,
    val id: Int,
    val inspectionCategoryMaster: InspectionCategoryMaster,
    val inspectionLocation: String,
    val inspectionType: InspectionType,
    val isActive: Boolean,
    val site: SiteXX,
    val version: Int
)