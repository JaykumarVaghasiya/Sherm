package com.jay.shermassignment.ui.corrective_action_details

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.jay.shermassignment.R
import com.jay.shermassignment.generic.showGenericDateDialog
import com.jay.shermassignment.generic.showToast
import com.jay.shermassignment.ui.dueDate.DueDateExtendRequest
import com.jay.shermassignment.ui.dueDate.DueDateExtendedApproval
import com.jay.shermassignment.utils.SessionManager
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CAViewActivity : AppCompatActivity() {
    private lateinit var dueDateRequest: MaterialButton
    private lateinit var dueDateApproval: MaterialButton
    private lateinit var dueDateButton: MaterialButton
    private lateinit var dueDateText: MaterialTextView
    private lateinit var reviewDateText: MaterialTextView
    private lateinit var caResponsiblePersonSpinner: Spinner
    private lateinit var hierarchyOfControlSpinner: Spinner
    private lateinit var statusSpinner: Spinner
    private lateinit var followUpSpinner: Spinner
    private lateinit var correctiveActionText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_caview)
        supportActionBar?.setTitle(R.string.add_inspection)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val authToken = SessionManager(this).fetchAuthToken()
        initializeViews()
        setClickListeners()

        lifecycleScope.launch {
            fetchData(authToken)
        }
    }

    private fun initializeViews() {
        dueDateRequest = findViewById(R.id.btnDueDateRequest)
        dueDateApproval = findViewById(R.id.btnDueDateApproval)
        dueDateButton = findViewById(R.id.btnDueDate)
        dueDateText = findViewById(R.id.tvDueDateCA)
        reviewDateText = findViewById(R.id.tvReviewDate)
        caResponsiblePersonSpinner = findViewById(R.id.spResponsiblePersonCA)
        hierarchyOfControlSpinner = findViewById(R.id.spHierarchyOfControl)
        statusSpinner = findViewById(R.id.spStatus)
        followUpSpinner = findViewById(R.id.spFollowUp)
        correctiveActionText = findViewById(R.id.etmCorrectiveAction)
    }


    private fun setClickListeners() {
        dueDateRequest.setOnClickListener { navigateToDueDateExtendRequest() }
        dueDateApproval.setOnClickListener { navigateToDueDateExtendedApproval() }
        dueDateButton.setOnClickListener { showDueDatePicker() }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->{
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun navigateToDueDateExtendRequest() {
        val id = intent.getIntExtra("correctiveActionId", 1)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        navigateToActivity(DueDateExtendRequest::class.java, id)
    }

    private fun navigateToDueDateExtendedApproval() {
        val id = intent.getIntExtra("correctiveActionId", 1)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        navigateToActivity(DueDateExtendedApproval::class.java, id)
    }

    private suspend fun fetchData(authToken: String?) {
        val id = intent.getIntExtra("correctiveActionId", 1)
        val cAViewResponse = try {
            CAViewInstance.api.getAllCAViewDetails(id, authToken!!)
        } catch (e: Exception) {
            showToast(e.message)
            return
        }

        if (cAViewResponse.isSuccessful && cAViewResponse.body() != null) {
            val cAViewBody = cAViewResponse.body()!!.content
            setupSpinnerWithArray(caResponsiblePersonSpinner, R.array.responsible_person, cAViewBody.responsiblePersonName)
            setupSpinnerWithArray(hierarchyOfControlSpinner, R.array.assignCA, cAViewBody.hierarchyOfControl.value)
            setupSpinnerWithArray(statusSpinner, R.array.status, cAViewBody.status.toString())
            setupSpinnerWithArray(followUpSpinner, R.array.followUp,
                cAViewBody.reviewDate?.toString()
            )
            correctiveActionText.setText(cAViewBody.action)

            val originalDate = cAViewBody.dueDate
            val originalDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val formattedDate = originalDateFormat.parse(originalDate)

            val desiredDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.US)
            val formattedDateString = formattedDate?.let { desiredDateFormat.format(it) }

            dueDateText.text = formattedDateString
            reviewDateText.text = cAViewBody.reviewDate?.toString()
        }
    }

    private fun setupSpinnerWithArray(spinner: Spinner, arrayResId: Int, selectedItem: String?) {
        val items = resources.getStringArray(arrayResId)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        if (!selectedItem.isNullOrBlank()) {
            val selectedIndex = items.indexOf(selectedItem)
            if (selectedIndex != -1) {
                spinner.setSelection(selectedIndex)
            }
        }
    }


    private fun showDueDatePicker() {
        showGenericDateDialog(R.string.selectedDate.toString(), System.currentTimeMillis()) { selectedDate ->
            val formattedDate = SimpleDateFormat("dd-MM-yyyy", Locale.US).format(Date(selectedDate))
            reviewDateText.text = formattedDate
        }
    }

    private fun <T> navigateToActivity(clazz: Class<T>, id: Int) {
        val intent = Intent(this, clazz)
        intent.putExtra("id", id)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
    }
}
