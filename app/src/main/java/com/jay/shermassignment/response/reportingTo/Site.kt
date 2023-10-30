package com.jay.shermassignment.response.reportingTo

data class Site(
    val active: Boolean,
    val address: AddressXX,
    val description: String,
    val document: Document,
    val id: Int,
    val isDisplayInChart: Boolean,
    val isSiteAdminCanDelete: Boolean,
    val maxEmployee: Any,
    val name: String,
    val parentSite: Any,
    val phone: String,
    val `private`: Boolean,
    val siteDisplayInReport: Boolean,
    val siteIsPrimary: Boolean,
    val timezone: String,
    val version: Int
)