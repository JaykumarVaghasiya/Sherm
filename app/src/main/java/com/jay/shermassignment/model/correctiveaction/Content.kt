package com.jay.shermassignment.model.correctiveaction

data class Content(
    val page: Int,
    val records: Int,
    val reportRows: Any,
    val rows: List<Row>,
    val total: Int
)