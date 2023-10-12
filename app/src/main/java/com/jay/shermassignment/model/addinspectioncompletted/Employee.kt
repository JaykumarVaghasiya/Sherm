package com.jay.shermassignment.model.addinspectioncompletted

data class Employee(
    val accessForTrainingOnly: Boolean,
    val active: Boolean,
    val address: Address,
    val agreement: Any,
    val bloodGroup: String,
    val companyName: Any,
    val contractor: Any,
    val contractorEmployee: Boolean,
    val contractorsDelegateKBF: Boolean,
    val dateofBirth: String,
    val definedField1: Any,
    val definedField2: Any,
    val definedField3: Any,
    val department: Department,
    val email: String,
    val emergency: Emergency,
    val emergencyEmail: String,
    val emergencyMobile: String,
    val emergencyPhone: String,
    val employeeActive: Boolean,
    val employeeId: String,
    val employeeStatusHistoryId: Any,
    val employmentType: EmploymentType,
    val firstName: String,
    val fullName: String,
    val gender: String,
    val id: Int,
    val joiningDate: String,
    val lastName: String,
    val mobile: String,
    val nextOfKin: String,
    val otherEmployee: Boolean,
    val periodFrom: Any,
    val periodTo: Any,
    val phone: String,
    val position: Position,
    val positionName: Any,
    val relationship: String,
    val reportingToName: Any,
    val site: Site,
    val status: Any,
    val taxFileNumber: Any,
    val terminationDate: Any,
    val userRole: Any,
    val userType: Any,
    val version: Int,
    val workHours: Any
)