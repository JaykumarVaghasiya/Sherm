package com.jay.shermassignment.generic

import android.content.Context
import androidx.appcompat.app.AlertDialog

fun showCustomDialog(
    context: Context,
    titleResId: Int,
    messageResId: Int,
    positiveButtonLabel: String = "OK",
    onPositiveButtonClick: () -> Unit = {}
) {
    val title = context.getString(titleResId)
    val message = context.getString(messageResId)

    AlertDialog.Builder(context)
        .setMessage(message)
        .setTitle(title)
        .setCancelable(true)
        .setPositiveButton(positiveButtonLabel) { dialog, _ ->
            dialog.dismiss()
            onPositiveButtonClick()
        }
        .create()
        .show()
}