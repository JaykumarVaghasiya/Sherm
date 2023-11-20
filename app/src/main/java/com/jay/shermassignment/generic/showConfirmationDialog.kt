package com.jay.shermassignment.generic

import android.app.Activity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Activity.showConfirmationDialog(
    titleResId: String?,
    messageResId: String?,
    positiveButtonLabel: String = "OK",
    onPositiveButtonClick: () -> Unit = {}
) {

    MaterialAlertDialogBuilder(this)
        .setMessage(messageResId)
        .setTitle(titleResId)
        .setCancelable(true)
        .setPositiveButton(positiveButtonLabel) { dialog, _ ->
            dialog.dismiss()
            onPositiveButtonClick()
        }
        .create()
        .show()
}