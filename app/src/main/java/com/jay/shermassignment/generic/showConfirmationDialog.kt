package com.jay.shermassignment.generic

import android.app.Activity
import androidx.appcompat.app.AlertDialog

fun Activity.showConfirmationDialog(
    titleResId: Int,
    messageResId: Int,
    positiveButtonLabel: String = "OK",
    onPositiveButtonClick: () -> Unit = {}
) {
    val title = getString(titleResId)
    val message =getString(messageResId)

    AlertDialog.Builder(this)
        .setMessage(message)
        .setTitle(title)
        .setCancelable(true)
        .setPositiveButton(positiveButtonLabel) { dialog, _ ->
            dialog.dismiss()
        }
        .create()
        .show()
}