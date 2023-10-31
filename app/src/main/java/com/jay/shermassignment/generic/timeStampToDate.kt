package com.jay.shermassignment.generic

import java.text.SimpleDateFormat
import java.util.Locale

fun timestampToDate(inputDate: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    return try {
        val date = inputFormat.parse(inputDate)?: null
        outputFormat.format(date!!)
    } catch (e: Exception) {
        "Invalid Date Format"
    }

}