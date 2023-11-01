package com.jay.shermassignment.ui.add_corrective_action

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.jay.shermassignment.R
import com.jay.shermassignment.api.inspection.SpinnerInstance
import com.jay.shermassignment.generic.commonDateToISODate
import com.jay.shermassignment.generic.setupSpinnerFromArray
import com.jay.shermassignment.generic.showConfirmationDialog
import com.jay.shermassignment.generic.showGenericDateDialog
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
    private lateinit var loading:LinearLayout
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
                showConfirmationDialog(getString(R.string.sherm),e.message)
                return@launch
            } catch (e: HttpException) {
                showConfirmationDialog(getString(R.string.sherm),e.message)
                return@launch
            } catch (e: IOException) {
                showConfirmationDialog(getString(R.string.sherm),e.message)
                return@launch
            }

            if (responsiblePerson.isSuccessful && responsiblePerson.body() != null) {
                val body = responsiblePerson.body()
                loading.visibility=View.GONE
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
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }
                    }

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
        loading=findViewById(R.id.overLay)
        loading.bringToFront()
        loading.visibility=View.VISIBLE
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
        val reviewDateValue = reviewDateTextView.text.toString()
        val authToken = SessionManager(this).fetchAuthToken()
        inspectionScheduleId = intent.getIntExtra("iId", 1)



        val body = AddCorrectiveActionBody(
            null,
            correctiveAction,
            commonDateToISODate(dueDateText),
            null,
            HierarchyOfControl(hierarchyOfControlValue),
            null,
            inspectionScheduleId,
            null,
            responsibleId,
            commonDateToISODate(reviewDateValue)
        )

        lifecycleScope.launch {
            val addCorrectiveAction = try {
                AddCorrectiveActionInstance.api.addCorrectiveAction(body, "Bearer $authToken")
            } catch (e: Exception) {
                showConfirmationDialog(getString(R.string.error),e.message)
                return@launch
            } catch (e: HttpException) {
                showConfirmationDialog(getString(R.string.error),e.message)
                return@launch
            } catch (e: IOException) {
                showConfirmationDialog(getString(R.string.error),e.message)
                return@launch
            }
            if (addCorrectiveAction.isSuccessful && addCorrectiveAction.body() != null) {
                loading.visibility=View.GONE
                showConfirmationDialog(getString(R.string.sucess),getString(R.string.added)){
                    finish()
                }
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
                    SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date(selectedDate))
                dueDateTextView.text = formattedDate

                // Calculate and display the review date based on the selected follow-up duration

                calculateReviewDate()
            }
        }
        followUpSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                calculateReviewDate()
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Do nothing
            }
        }
    }

    private fun calculateReviewDate() {
        val selectedFollowUpItem = followUpSpinner.selectedItem.toString()
        val followUpMonths = selectedFollowUpItem.split(" ")[0].toInt()

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = selectedDueDate
        calendar.add(Calendar.MONTH, followUpMonths)

        val formattedReviewDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(calendar.time)
        reviewDateTextView.text = formattedReviewDate
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> {
                loading.visibility=View.VISIBLE
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