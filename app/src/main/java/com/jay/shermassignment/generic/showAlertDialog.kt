package com.jay.shermassignment.generic

import android.content.Context
import androidx.appcompat.app.AlertDialog

fun showAlertDialog(
    context: Context,
    title: String,
    message: String,
    positiveButtonLabel: String = "OK",
    negativeButtonLabel: String? = null,
    onPositiveButtonClick: () -> Unit = {},
    onNegativeButtonClick: () -> Unit = {}
) {
    val builder = AlertDialog.Builder(context)
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