package com.jay.shermassignment.dataModel.comments

data class Assigner(
    val id: Int,
    val user: User,
    val userdetail: Userdetail,
    val version: Int
)