package com.jay.shermassignment.dataModel.showInspection

data class SiteX(
    val active: Boolean,
    val address: AddressX,
    val description: String,
    val document: Document,
    val id: Int,
    val isDisplayInChart: Boolean,
    val isSiteAdminCanDelete: Any,
    val maxEmployee: Any,
    val name: String,
    val parentSite: Any,
    val phone: String,
    val `private`: Boolean,
    val siteDisplayInReport: Any,
    val siteIsPrimary: Any,
    val timezone: String,
    val version: Int
)