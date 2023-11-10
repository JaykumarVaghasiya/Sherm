package com.jay.shermassignment.dataModel.inspection

data class Content(
    val page: Int,
    val records: Int,
    val reportRows: Any,
    val rows: List<Row>,
    val total: Int
)