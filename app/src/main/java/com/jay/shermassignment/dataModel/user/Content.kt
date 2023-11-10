package com.jay.shermassignment.dataModel.user

data class Content(
    val accountNonExpired: Boolean,
    val accountNonLocked: Boolean,
    val active: Boolean,
    val admin: Boolean,
    val authorities: List<Authority>,
    val clientId: Int,
    val credentialsNonExpired: Boolean,
    val employee: Employee,
    val employeeActive: Boolean,
    val enabled: Boolean,
    val firstTime: Boolean,
    val grantedAuthorities: List<GrantedAuthority>,
    val id: Int,
    val isAdmin: Boolean,
    val isDashboardMessagesAllowed: Boolean,
    val isNotificationEmailsAllowed: Boolean,
    val isSecondaryAdmin: Boolean,
    val lastPasswordResetDate: Long,
    val password: String,
    val secondaryAdmin: Boolean,
    val token: String,
    val username: String
)