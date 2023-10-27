package com.jay.shermassignment.ui.add_corrective_action

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.jay.shermassignment.R
import com.jay.shermassignment.generic.setupSpinnerFromArray
import com.jay.shermassignment.generic.showGenericDateDialog
import com.jay.shermassignment.generic.showToast
import com.jay.shermassignment.utils.SessionManager
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AddCorrectiveAction : AppCompatActivity() {

    private lateinit var responsiblePersonSpinner: Spinner
    private lateinit var hierarchyOfControlSpinner: Spinner
    private lateinit var statusSpinner: Spinner
    private lateinit var dueDateTextView: MaterialTextView
    private lateinit var dueDateButton: MaterialButton
    private lateinit var correctiveActionMultilineEditText: EditText
    private lateinit var followUpSpinner: Spinner
    private lateinit var reviewDateTextView: MaterialTextView
    private var inspectionScheduleId: Int = 0
    private var selectedDueDate: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assign_corrective_action)
        supportActionBar?.setTitle(R.string.assign_ca)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        inspectionScheduleId=intent.getIntExtra("ids",0)
        initializeViewId()
        spinnerValue()
        btClickListener()
        spinnerValue()
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

    private fun spinnerValue() {

//        setupSpinnerFromArray(responsiblePersonSpinner, R.array.responsible_person)
        setupSpinnerFromArray(hierarchyOfControlSpinner, R.array.hierarchyControl)
        setupSpinnerFromArray(followUpSpinner, R.array.followUp)
        setupSpinnerFromArray(statusSpinner, R.array.status)
    }


    private fun gatherData() {

        val responsiblePerson = responsiblePersonSpinner.selectedItem.toString()
        val responsiblePersonValue = getResponsiblePerson(responsiblePerson)
        val hierarchyOfControl = hierarchyOfControlSpinner.selectedItem.toString()
        val hierarchyOfControlValue = getHierarchyControl(hierarchyOfControl)
        val correctiveAction = correctiveActionMultilineEditText.text.toString()
        val dueDateText = dueDateTextView.text.toString()
        val status = statusSpinner.selectedItem.toString()
        val statusValue = getStatus(status)
        followUpSpinner.selectedItem.toString()
        val reviewDateValue = reviewDateTextView.text.toString()

        followUpSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                val calendar = Calendar.getInstance()
                calendar.add(Calendar.MONTH, position + 1)
                val dateAfterMonths = calendar.time

                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val formattedDate = dateFormat.format(dateAfterMonths)
                reviewDateTextView.text = formattedDate
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Do nothing
            }
        }


        val body = AddCorrectiveActionBody(
            correctiveAction,
            dueDateText,
            2888,
            HierarchyOfControl(hierarchyOfControlValue),
            9573,
            null,
            null,
            null,
            reviewDateValue,
            responsiblePersonValue
        )
        saveCorrectiveAction(body)
    }

    private fun btClickListener() {
        dueDateButton.setOnClickListener {
            showGenericDateDialog(
                R.string.selectedDate.toString(),
                System.currentTimeMillis()
            ) { selectedDate ->
                selectedDueDate = selectedDate
                val formattedDate =
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(selectedDate))
                dueDateTextView.text = formattedDate
                calculateAndDisplayReviewDate(selectedDueDate)
            }
        }
    }

    private fun calculateAndDisplayReviewDate(dueDate: Long) {

        val formattedReviewDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(dueDate))
        reviewDateTextView.text = formattedReviewDate

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> {
                gatherData()
                finish()
            }
            android.R.id.home ->{
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveCorrectiveAction(body: AddCorrectiveActionBody) {
        val authToken = SessionManager(this).fetchAuthToken()
        lifecycleScope.launch {
            val addCorrectiveAction = try {
                AddCorrectiveActionInstance.api.addCorrectiveAction(body, authToken!!)
            } catch (e: Exception) {
                showToast(e.message)
                return@launch
            } catch (e: HttpException) {
                showToast(e.message)
                return@launch
            } catch (e: IOException) {
                showToast(e.message)
                return@launch
            }
            if (addCorrectiveAction.isSuccessful && addCorrectiveAction.body() != null) {
                showToast(getString(R.string.added))
            }
        }

    }

    private fun getResponsiblePerson(responsibleSpinner: String): Int {
        return when (responsibleSpinner) {
            "Caroline Kingston" -> 1
            "Test Supervisor" -> 2
            "Test Manager" -> 3
            "Test Employee" -> 248
            else -> 0
        }
    }

    private fun getHierarchyControl(hierarchyOfControl: String): Int {
        return when (hierarchyOfControl) {
            "Elimination" -> 1
            "Substitution" -> 2
            "Engineering" -> 3
            "Isolation" -> 4
            "Administration" -> 5
            else -> 6
        }
    }

    private fun getStatus(status: String): Int {
        return when (status) {
            "Assigned" -> 1
            else -> 2
        }
    }

}