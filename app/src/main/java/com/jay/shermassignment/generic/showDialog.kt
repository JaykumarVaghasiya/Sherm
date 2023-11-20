package com.jay.shermassignment.generic

import android.app.Activity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Activity.showCustomDialog(
    titleResId: String?,
    messageResId: String?,
    positiveButtonLabel: String = "Yes",
    onPositiveButtonClick: () -> Unit = {}
) {


    MaterialAlertDialogBuilder(this)
        .setMessage(messageResId)
        .setTitle(titleResId)
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