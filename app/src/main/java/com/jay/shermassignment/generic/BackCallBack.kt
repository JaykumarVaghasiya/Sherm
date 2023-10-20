package com.jay.shermassignment.generic

import androidx.activity.OnBackPressedCallback

class BackCallBack(private val callback: () -> Unit) : OnBackPressedCallback(true) {
    override fun handleOnBackPressed() {
        callback()
    }
}
