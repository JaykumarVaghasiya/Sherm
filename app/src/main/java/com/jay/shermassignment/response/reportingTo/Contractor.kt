package com.jay.shermassignment.response.reportingTo

data class Contractor(
    val abnnumber: String,
    val address: Address,
    val certificateOne: Any,
    val certificateThree: Any,
    val certificateTwo: Any,
    val contactPersonName: String,
    val contractorName: String,
    val contractorType: String,
    val email: String,
    val fax: String,
    val id: Int,
    val inactiveDate: Any,
    val isExpiryReminderSent: Boolean,
    val isMotoVehiInsuNotRequired: Any,
    val mobile: String,
    val motorVehicleIncExpiryDate: Any,
    val motorVehicleInsNo: Any,
    val motorVehicleInsuranceCertificate: Any,
    val motorVehicleInsurer: Any,
    val phone: String,
    val professionalIndemnityExpiryDate: String,
    val professionalIndemnityInsNo: String,
    val professionalIndemnityInsurer: String,
    val professionalLimitLiability: String,
    val publicLiabilityExpiryDate: String,
    val publicLiabilityInsuranceNo: String,
    val publicLiabilityInsurer: String,
    val publicLimitLiability: String,
    val status: String,
    val version: Int,
    val workCoverExpiryDate: Any,
    val workCoverInsuranceNo: Any,
    val workCoverInsurer: Any,
    val workDescription: String
)