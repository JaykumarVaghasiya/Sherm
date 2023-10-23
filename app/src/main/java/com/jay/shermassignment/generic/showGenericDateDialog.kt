package com.jay.shermassignment.generic

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker

fun Activity.showGenericDateDialog(
    title: String,
    initialSelection: Long,
    onDateSelected: (Long) -> Unit
) {
    val constraintsBuilder = CalendarConstraints.Builder()
    val currentDate = MaterialDatePicker.todayInUtcMilliseconds()
    constraintsBuilder.setStart(currentDate)

    val datePicker = MaterialDatePicker.Builder.datePicker()
        .setTitleText(title)
        .setSelection(initialSelection)
        .build()

    datePicker.addOnPositiveButtonClickListener { selection ->
        onDateSelected(selection)
    }

    datePicker.addOnNegativeButtonClickListener {
        datePicker.dismiss()
    }

    datePicker.show((this as AppCompatActivity).supportFragmentManager, "")
}