package com.jay.shermassignment.dataModel.addinspection

data class InspectionType(
    val activeFormBuilder: Boolean,
    val description: Any,
    val formTemplate: Any,
    val frequency: String,
    val id: Int,
    val inActive: Boolean,
    val name: String,
    val version: Int
)