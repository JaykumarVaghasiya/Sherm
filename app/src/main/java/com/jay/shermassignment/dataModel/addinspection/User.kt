package com.jay.shermassignment.dataModel.addinspection

data class User(
    val accountNonExpired: Boolean,
    val accountNonLocked: Boolean,
    val active: Boolean,
    val admin: Boolean,
    val authorities: List<Any>,
    val clientId: Any,
    val credentialsNonExpired: Boolean,
    val employee: Employee,
    val employeeActive: Boolean,
    val enabled: Boolean,
    val firstTime: Boolean,
    val grantedAuthorities: List<Any>,
    val id: Any,
    val isAdmin: Boolean,
    val isDashboardMessagesAllowed: Boolean,
    val isNotificationEmailsAllowed: Boolean,
    val isSecondaryAdmin: Boolean,
    val lastPasswordResetDate: Long,
    val password: String,
    val secondaryAdmin: Boolean,
    val token: Any,
    val username: String
)