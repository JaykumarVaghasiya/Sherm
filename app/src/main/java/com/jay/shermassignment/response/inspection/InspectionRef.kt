package com.jay.shermassignment.response.inspection

import com.google.gson.annotations.SerializedName

data class InspectionRef(
    val inspectionId: String,
    val inspectionLocation: String,
    val page: Int,
    val responsibleId: Any?,
    val sidx: String,
    @SerializedName("siteI(d")
    val siteId: List<Any>,
    val sord: String,
    val status: String
)