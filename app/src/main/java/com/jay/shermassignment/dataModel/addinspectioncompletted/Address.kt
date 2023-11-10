package com.jay.shermassignment.dataModel.addinspectioncompletted

data class Address(
    val address: String,
    val city: String,
    val country: Country,
    val poBoxNo: Any,
    val postCode: String,
    val state: State,
    val street: Any
)