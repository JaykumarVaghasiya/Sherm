package com.jay.shermassignment.response.user

data class Position(
    val active: Boolean,
    val description: String,
    val id: Int,
    val name: String,
    val qualification: String,
    val riskActivities: String,
    val version: Int
)