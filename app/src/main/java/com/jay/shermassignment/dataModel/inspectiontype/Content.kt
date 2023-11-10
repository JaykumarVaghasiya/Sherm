package com.jay.shermassignment.dataModel.inspectiontype

data class Content(
    val activeFormBuilder: Boolean,
    val description: String,
    val formTemplate: Any,
    val frequency: String,
    val id: Int,
    val inActive: Boolean,
    val name: String,
    val version: Int
)