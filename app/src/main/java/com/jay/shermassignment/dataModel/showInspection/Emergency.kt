package com.jay.shermassignment.dataModel.showInspection

data class Emergency(
    val address: String,
    val city: String,
    val country: Country,
    val poBoxNo: Any,
    val postCode: String,
    val state: State,
    val street: Any
)