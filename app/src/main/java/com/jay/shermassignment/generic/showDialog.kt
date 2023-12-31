package com.jay.shermassignment.generic

import android.app.Activity
import androidx.appcompat.app.AlertDialog

fun Activity.showCustomDialog(
    titleResId: Int,
    messageResId: Int,
    positiveButtonLabel: String = "Yes",
    onPositiveButtonClick: () -> Unit = {}
) {
    val title = getString(titleResId)
    val message =getString(messageResId)

    AlertDialog.Builder(this)
        .setMessage(message)
        .setTitle(title)
        .setCancelable(true)
        .setPositiveButton(positiveButtonLabel) { dialog, _ ->
            onPositiveButtonClick()
            dialog.dismiss()
        }
        .setNegativeButton("No"){dialog,_ ->
            dialog.dismiss()
        }
        .create()
        .show()
}