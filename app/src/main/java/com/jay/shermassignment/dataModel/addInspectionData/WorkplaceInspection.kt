package com.jay.shermassignment.dataModel.addInspectionData

data class WorkplaceInspection(
    val id: Int,
    val inspectionCategoryMaster: InspectionCategoryMaster,
    val site: Site
)