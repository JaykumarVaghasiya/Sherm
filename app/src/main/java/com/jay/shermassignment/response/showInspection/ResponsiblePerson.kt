package com.jay.shermassignment.response.showInspection

data class ResponsiblePerson(
    val id: Int,
    val user: User,
    val userdetail: Userdetail,
    val version: Int
)