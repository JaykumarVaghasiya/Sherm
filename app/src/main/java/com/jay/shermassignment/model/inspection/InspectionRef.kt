package com.jay.shermassignment.model.inspection

data class InspectionRef(
    val inspectionId: String,
    val inspectionLocation: String,
    val page: Int,
    val responsibleId: Any,
    val sidx: String,
    val siteId: List<Any>,
    val sord: String,
    val status: String
)