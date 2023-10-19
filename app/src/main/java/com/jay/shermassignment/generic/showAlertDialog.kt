package com.jay.shermassignment.generic

import android.content.Context
import androidx.appcompat.app.AlertDialog

fun showAlertDialog(context: Context, title: String, message: String) {
    val builder = AlertDialog.Builder(context)
    builder.setTitle(title)
    builder.setMessage(message)
    builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
    builder.setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
    builder.create().show()
}