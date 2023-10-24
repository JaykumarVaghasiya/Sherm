package com.jay.shermassignment.response.correctiveaction

data class Content(
    val page: Int,
    val records: Int,
    val reportRows: Any,
    val rows: List<Row>,
    val total: Int
)