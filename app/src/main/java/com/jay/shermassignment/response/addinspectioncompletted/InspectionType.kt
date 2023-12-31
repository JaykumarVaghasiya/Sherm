package com.jay.shermassignment.response.addinspectioncompletted

data class InspectionType(
    val activeFormBuilder: Boolean,
    val description: String,
    val formTemplate: Any,
    val frequency: String,
    val id: Int,
    val inActive: Boolean,
    val name: String,
    val version: Int
)