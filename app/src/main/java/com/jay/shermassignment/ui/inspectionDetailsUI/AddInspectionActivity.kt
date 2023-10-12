package com.jay.shermassignment.ui.inspectionDetailsUI

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textview.MaterialTextView
import com.jay.shermassignment.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class AddInspectionActivity : AppCompatActivity() {

    private lateinit var categorySpinner: Spinner
    private lateinit var inspectionTypeSpinner: Spinner
    private lateinit var inspectionLocationSpinner: Spinner
    private lateinit var site: Spinner
    private lateinit var reportingTo: Spinner
    private lateinit var responsidePersonSpinner: Spinner
    private lateinit var dueDate: MaterialTextView
    private lateinit var dateButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_inspection_actitvtity)

        categorySpinner = findViewById(R.id.spCategory)
        inspectionTypeSpinner = findViewById(R.id.spInspectionType)
        inspectionLocationSpinner = findViewById(R.id.spInspectionLocation)
        site = findViewById(R.id.spSite)
        reportingTo = findViewById(R.id.spReportingTo)
        responsidePersonSpinner = findViewById(R.id.spResponsiblePerson)
        dueDate = findViewById(R.id.tvDueDate)
        dateButton = findViewById(R.id.btCalender)
        spinnerValues()

        dateButton.setOnClickListener {
            showDateDialog()
        }


    }

    private fun setupSpinnerFromArray(spinner: Spinner, arrayResId: Int) {
        ArrayAdapter.createFromResource(this, arrayResId, android.R.layout.simple_spinner_item)
            .also { arrayAdapter ->
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = arrayAdapter
            }
    }

    private fun showDateDialog() {
        val constraintsBuilder = CalendarConstraints.Builder()
        val currentDate = MaterialDatePicker.todayInUtcMilliseconds()
        constraintsBuilder.setStart(currentDate)

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select Date")
            .setSelection(currentDate) // You can set the initial selected date if needed
            .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.timeInMillis = selection
            val selectedDateString =
                SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(calendar.time)
            dueDate.text = selectedDateString
        }
        datePicker.addOnNegativeButtonClickListener {
            datePicker.dismiss()
        }


        datePicker.show(supportFragmentManager, "")
    }

    private fun spinnerValues() {

        setupSpinnerFromArray(categorySpinner, R.array.category)
        setupSpinnerFromArray(inspectionTypeSpinner, R.array.category_work_health_and_safety)
        setupSpinnerFromArray(site, R.array.site)
        setupSpinnerFromArray(inspectionLocationSpinner, R.array.inspection_location)
        setupSpinnerFromArray(reportingTo, R.array.responsible_person)
        setupSpinnerFromArray(responsidePersonSpinner, R.array.responsible_person)
    }
}

