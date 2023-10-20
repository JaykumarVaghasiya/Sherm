package com.jay.shermassignment.generic

import android.app.Activity
import android.content.Context
import android.content.Intent

inline fun <reified T : Activity> Context.startActivityStart(intent: Intent? = null) {
    val targetIntent = intent ?: Intent(this, T::class.java)
    startActivity(targetIntent)
}