package com.jay.shermassignment.model.showInspection

data class AddressX(
    val address: String,
    val city: String,
    val country: Country,
    val poBoxNo: String,
    val postCode: String,
    val state: State,
    val street: String
)