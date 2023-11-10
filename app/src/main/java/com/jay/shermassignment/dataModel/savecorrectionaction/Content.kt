package com.jay.shermassignment.dataModel.savecorrectionaction

data class Content(
    val action: String,
    val beforeReviewDateReminderSent: Boolean,
    val createdOn: String,
    val dueDate: Long,
    val existingControl: Boolean,
    val hasDueDateExtension: Boolean,
    val hierarchyOfControl: HierarchyOfControlX,
    val id: Int,
    val lastStatusChangeDate: String,
    val outOfDueDateReminderSent: Boolean,
    val parentId: Int,
    val parentType: String,
    val reviewDate: Long,
    val reviewDueDateReminderSent: Boolean,
    val status: Int,
    val version: Int
)