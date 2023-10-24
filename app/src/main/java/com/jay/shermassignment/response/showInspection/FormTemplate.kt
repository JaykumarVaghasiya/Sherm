package com.jay.shermassignment.response.showInspection

data class FormTemplate(
    val formName: String,
    val id: Int,
    val publish: Boolean,
    val version: Int
)