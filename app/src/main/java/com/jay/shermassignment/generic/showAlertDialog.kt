package com.jay.shermassignment.generic

import android.app.Activity
import androidx.appcompat.app.AlertDialog

fun Activity.showAlertDialog(
    title: String,
    message: String,
    positiveButtonLabel: String = "OK",
    negativeButtonLabel: String? = null,
    onPositiveButtonClick: () -> Unit = {},
    onNegativeButtonClick: () -> Unit = {}
) {
    val builder = AlertDialog.Builder(this)
    builder.setTitle(title)
    builder.setMessage(message)
    builder.setPositiveButton(positiveButtonLabel) { dialog, _ ->
        dialog.dismiss()
        onPositiveButtonClick()
    }
    negativeButtonLabel?.let {
        builder.setNegativeButton(it) { dialog, _ ->
            dialog.dismiss()
            onNegativeButtonClick()
        }
    }
    builder.create().show()
}