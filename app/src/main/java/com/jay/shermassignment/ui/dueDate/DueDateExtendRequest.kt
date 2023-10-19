package com.jay.shermassignment.ui.dueDate

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.jay.shermassignment.R
import com.jay.shermassignment.generic.showGenericDateDialog
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DueDateExtendRequest : AppCompatActivity() {

    private lateinit var preferredDateText:MaterialTextView
    private lateinit var preferredDateButton: MaterialButton
    private lateinit var commentText:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_due_date_extend_request)

        initializeViewsId()

        preferredDateButton.setOnClickListener {
            showGenericDateDialog(R.string.selectedDate.toString(), System.currentTimeMillis() , { selectedDate ->
                val formattedDate = SimpleDateFormat("dd-MM-yyyy", Locale.US).format(Date(selectedDate))
                preferredDateText.text = formattedDate
            }, this)
        }






    }
    private fun initializeViewsId() {
        preferredDateText=findViewById(R.id.tvPreferredDate)
        preferredDateButton=findViewById(R.id.btnPreferredDate)
        commentText=findViewById(R.id.etComments)

    }

}