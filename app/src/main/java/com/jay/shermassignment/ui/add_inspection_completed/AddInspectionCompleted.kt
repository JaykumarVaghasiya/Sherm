package com.jay.shermassignment.ui.add_inspection_completed

import android.os.Bundle
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
        supportActionBar?.setTitle(R.string.add_inspections_completed)

        initializeId()
        buttonClickListener()



    }

    private fun buttonClickListener() {
        calendarInspectionCompleted.setOnClickListener {
            showGenericDateDialog(R.string.selectedDate.toString(), System.currentTimeMillis()) { selectedDate ->
                val formattedDate =
                    SimpleDateFormat("dd-MM-yyyy", Locale.US).format(Date(selectedDate))
                inspectionCompleted.text = formattedDate
            }
        }
    }

    private fun initializeId(){
        inspectionCompleted=findViewById(R.id.tvInspectionCompletedDate)
        calendarInspectionCompleted=findViewById(R.id.btnInspectionCompleted)
    }
}