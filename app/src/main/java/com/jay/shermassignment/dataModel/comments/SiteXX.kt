package com.jay.shermassignment.dataModel.comments

data class SiteXX(
    val active: Boolean,
    val address: AddressX,
    val description: Any,
    val document: Document,
    val id: Int,
    val isDisplayInChart: Boolean,
    val isSiteAdminCanDelete: Any,
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