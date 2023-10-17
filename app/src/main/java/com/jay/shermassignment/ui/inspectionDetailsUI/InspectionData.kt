package com.jay.shermassignment.ui.inspectionDetailsUI

data class InspectionData (
    val category: String,
    val inspectionType:String,
    val site:String,
    val inspectionLocation:String,
    val responsiblePerson: String,
    val date:String,
    val reportingTo:String,
    val status:Boolean=false
)