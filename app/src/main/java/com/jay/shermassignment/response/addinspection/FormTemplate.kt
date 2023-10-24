package com.jay.shermassignment.response.addinspection

data class FormTemplate(
    val formName: String,
    val id: Int,
    val publish: Boolean,
    val version: Int
)