package com.jay.shermassignment.generic

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import com.google.android.material.button.MaterialButton
import com.jay.shermassignment.R

fun Activity.createCustomDialog(customLayoutResId: Int, onPositiveClick: () -> Unit): AlertDialog {
    val dialogView = LayoutInflater.from(this).inflate(customLayoutResId, null)

    val dialog = AlertDialog.Builder(this)
        .setView(dialogView)
        .create()

    val positiveButton = dialogView.findViewById<MaterialButton>(R.id.btnPositive)
    val negativeButton = dialogView.findViewById<MaterialButton>(R.id.btnNegative)

    positiveButton.setOnClickListener {
        onPositiveClick()
        dialog.dismiss()
    }

    negativeButton.setOnClickListener {
        dialog.dismiss()
    }

    return dialog
}