package com.jay.shermassignment.generic

import java.text.SimpleDateFormat
import java.util.Locale

fun commonDateToISODate(inputDate:String):String {
    val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    return try {
        val date = inputFormat.parse(inputDate)?: null
        outputFormat.format(date!!)
    } catch (e: Exception) {
        "Invalid Date Format"
    }

}