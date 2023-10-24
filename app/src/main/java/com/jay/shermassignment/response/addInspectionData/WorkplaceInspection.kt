package com.jay.shermassignment.response.addInspectionData

data class WorkplaceInspection(
    val id: Int,
    val inspectionCategoryMaster: InspectionCategoryMaster,
    val site: Site
)