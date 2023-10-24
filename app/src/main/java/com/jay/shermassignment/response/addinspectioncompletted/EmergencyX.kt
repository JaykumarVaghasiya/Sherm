package com.jay.shermassignment.response.addinspectioncompletted

data class EmergencyX(
    val address: String,
    val city: String,
    val country: Country,
    val poBoxNo: Any,
    val postCode: String,
    val state: State,
    val street: Any
)