package com.jay.shermassignment.generic

import android.app.Activity
import android.widget.ArrayAdapter
import android.widget.Spinner

fun Activity.setupSpinnerFromArray(spinner: Spinner, arrayResId: Int) {
    ArrayAdapter.createFromResource(this, arrayResId, android.R.layout.simple_spinner_item)
        .also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = arrayAdapter
        }
    val firstItem = spinner.getChildAt(0)
    if (firstItem != null) {
        firstItem.isEnabled = false
        firstItem.isClickable = false
    }
}
