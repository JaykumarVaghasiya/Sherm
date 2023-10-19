package com.jay.shermassignment.generic

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker

fun showGenericDateDialog(
    title: String,
    initialSelection: Long,
    onDateSelected: (Long) -> Unit,
    context: Context
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

    datePicker.show((context as AppCompatActivity).supportFragmentManager, "")
}