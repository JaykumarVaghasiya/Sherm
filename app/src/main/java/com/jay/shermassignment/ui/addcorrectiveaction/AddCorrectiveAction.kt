package com.jay.shermassignment.ui.addcorrectiveaction

import android.os.Bundle
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.jay.shermassignment.R

class AddCorrectiveAction : AppCompatActivity() {

    private lateinit var responsiblePersonSpinner: Spinner
    private lateinit var hierarchyOfControlSpinner: Spinner
    private lateinit var dueDateTextView: MaterialTextView
    private lateinit var dueDateButton: MaterialButton
    private lateinit var correctiveActionMultilineEditText: EditText
    private lateinit var statusSpinner: Spinner
    private lateinit var followUpSpinner: Spinner
    private lateinit var reviewDateTextView: MaterialTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assign_corrective_action)
        initializeViewId()

    }

    private fun initializeViewId() {
        responsiblePersonSpinner = findViewById(R.id.spResponsiblePersonAddCA)
        hierarchyOfControlSpinner = findViewById(R.id.spHierarchyOfControlAddCA)
        dueDateTextView = findViewById(R.id.tvDueDateAddCA)
        dueDateButton = findViewById(R.id.btnDuedateAddCA)
        correctiveActionMultilineEditText = findViewById(R.id.etmAddCA)
        statusSpinner = findViewById(R.id.spStatusAddCA)
        followUpSpinner = findViewById(R.id.spFollowUpAddCA)
        reviewDateTextView = findViewById(R.id.tvReviewDateAddCA)

    }
}