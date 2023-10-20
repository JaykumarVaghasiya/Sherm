package com.jay.shermassignment.ui.correctiveevaluation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jay.shermassignment.R
import com.jay.shermassignment.generic.BackCallBack

class CorrectiveEvaluation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_correcitve_evalution)
        supportActionBar?.setTitle(R.string.corrective_evaluation)

        backBtListener()
    }

    private fun backBtListener() {
        val onBackPressedCallback = BackCallBack{
            finish()
        }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }
}