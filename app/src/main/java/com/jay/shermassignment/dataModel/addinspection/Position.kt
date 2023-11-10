package com.jay.shermassignment.dataModel.addinspection

data class Position(
    val active: Boolean,
    val description: String,
    val id: Int,
    val name: String,
    val qualification: String,
    val riskActivities: String,
    val version: Int
)