package com.jay.shermassignment.model.addInspectionData

data class WorkplaceInspection(
    val id: Int,
    val inspectionCategoryMaster: InspectionCategoryMaster,
    val site: Site
)