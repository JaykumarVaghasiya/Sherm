package com.jay.shermassignment.ui.add_corrective_action

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.jay.shermassignment.R
import com.jay.shermassignment.api.inspection.SpinnerInstance
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
    private var responsibleId: Int = 0
    private var inspectionScheduleId: Int = 0
    private var selectedDueDate: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assign_corrective_action)
        supportActionBar?.setTitle(R.string.assign_ca)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initializeViewId()
        spinnerValue()
        getAllResponsiblePerson()
        btClickListener()
        spinnerValue()
    }

    private fun getAllResponsiblePerson() {

        val authToken = SessionManager(this).fetchAuthToken()
        lifecycleScope.launch {
            val responsiblePerson = try {
                SpinnerInstance.api.getAlLResponsiblePerson("Bearer $authToken")
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

            if (responsiblePerson.isSuccessful && responsiblePerson.body() != null) {
                val body = responsiblePerson.body()

                val responsiblePersonName = body?.content?.map { reportingTo ->
                    reportingTo.fullName
                }
                val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                    this@AddCorrectiveAction,
                    android.R.layout.simple_spinner_item,
                    responsiblePersonName ?: emptyList()
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                responsiblePersonSpinner.adapter = adapter
                responsiblePersonSpinner.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            responsibleId = body?.content?.get(position)?.id ?: 0
                            showToast("{$responsibleId}")
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }
                    }

            } else {
                showToast("Avengers Assemble ")
            }
        }
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
        setupSpinnerFromArray(hierarchyOfControlSpinner, R.array.hierarchyControl)
        setupSpinnerFromArray(followUpSpinner, R.array.followUp)
        setupSpinnerFromArray(statusSpinner, R.array.status)
    }


    private fun gatherData() {

        val hierarchyOfControl = hierarchyOfControlSpinner.selectedItem.toString()
        val hierarchyOfControlValue = getHierarchyControl(hierarchyOfControl)
        val correctiveAction = correctiveActionMultilineEditText.text.toString()
        val dueDateText = dueDateTextView.text.toString()
        followUpSpinner.selectedItem.toString()
        val reviewDateValue = reviewDateTextView.text.toString()
        val authToken = SessionManager(this).fetchAuthToken()
        inspectionScheduleId = intent.getIntExtra("iId", 1)

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
            null,
            correctiveAction,
            dueDateText,
            2888,
            HierarchyOfControl(hierarchyOfControlValue),
            null,
            null,
            null,
            responsibleId,
            reviewDateValue
        )

        lifecycleScope.launch {
            val addCorrectiveAction = try {
                AddCorrectiveActionInstance.api.addCorrectiveAction(body, "Bearer $authToken")
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
                finish()
            } else {
                showToast("FF")
            }
        }

    }

    private fun btClickListener() {
        dueDateButton.setOnClickListener {
            showGenericDateDialog(
                getString(R.string.selectedDate),
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
            }
            android.R.id.home ->{
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
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

}