package com.jay.shermassignment.generic

import android.app.Activity
import androidx.appcompat.app.AlertDialog

fun Activity.showCustomDialog(
    titleResId: String?,
    messageResId: String?,
    positiveButtonLabel: String = "Yes",
    onPositiveButtonClick: () -> Unit = {}
) {


    AlertDialog.Builder(this)
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