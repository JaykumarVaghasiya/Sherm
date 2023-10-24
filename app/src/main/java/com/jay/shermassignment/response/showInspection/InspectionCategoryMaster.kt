package com.jay.shermassignment.response.showInspection

data class InspectionCategoryMaster(
    val description: String,
    val id: Int,
    val isActive: Boolean,
    val name: String,
    val version: Int
)