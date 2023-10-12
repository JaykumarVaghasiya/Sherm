package com.jay.shermassignment.model.comments

data class Assigner(
    val id: Int,
    val user: User,
    val userdetail: Userdetail,
    val version: Int
)