package com.jay.shermassignment.ui.addinpectioncompleted

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.jay.shermassignment.R
import com.jay.shermassignment.generic.showGenericDateDialog
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddInspectionCompleted : AppCompatActivity() {

    private lateinit var inspectionCompleted:MaterialTextView
    private lateinit var calendarInspectionCompleted: MaterialButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_inspection_completed)

        initializeId()

        buttonClickListener()


        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })

    }

    private fun buttonClickListener() {
        calendarInspectionCompleted.setOnClickListener {
            showGenericDateDialog(R.string.selectedDate.toString(), System.currentTimeMillis() , { selectedDate ->
                val formattedDate = SimpleDateFormat("dd-MM-yyyy", Locale.US).format(Date(selectedDate))
                inspectionCompleted.text = formattedDate
            }, this)
        }
    }

    private fun initializeId(){
        inspectionCompleted=findViewById(R.id.tvInspectionCompletedDate)
        calendarInspectionCompleted=findViewById(R.id.btnInspectionCompleted)
    }
}