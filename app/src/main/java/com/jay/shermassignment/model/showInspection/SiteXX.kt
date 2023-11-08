package com.jay.shermassignment.model.showInspection

data class SiteXX(
    val active: Boolean,
    val address: AddressXX,
    val description: String,
    val document: DocumentX,
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