package com.jay.shermassignment.ui.correctiveactiondetails

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.jay.shermassignment.R
import com.jay.shermassignment.generic.showGenericDateDialog
import com.jay.shermassignment.ui.dueDate.DueDateExtendRequest
import com.jay.shermassignment.ui.dueDate.DueDateExtendedApproval
import com.jay.shermassignment.utils.SessionManager
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
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

        dueDateRequest.setOnClickListener {
            navigateToDueDateExtendRequest()
        }

        dueDateApproval.setOnClickListener {
            navigateToDueDateExtendedApproval()
        }

        dueDateButton.setOnClickListener {
            showGenericDateDialog(R.string.selectedDate.toString(), System.currentTimeMillis() , { selectedDate ->
                val formattedDate = SimpleDateFormat("dd-MM-yyyy", Locale.US).format(Date(selectedDate))
                dueDateText.text = formattedDate
            }, this)
        }
    }

    private fun navigateToDueDateExtendRequest() {
        val intent = Intent(this, DueDateExtendRequest::class.java)
        val id = intent.getIntExtra("correctiveActionId",1)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    private fun navigateToDueDateExtendedApproval() {
        val intent = Intent(this, DueDateExtendedApproval::class.java)
        val id = intent.getIntExtra("correctiveActionId",1)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    private suspend fun fetchData(authToken: String?) {
        val id = intent.getIntExtra("correctiveActionId",1)
        val cAViewResponse = try {

            CAViewInstance.api.getAllCAViewDetails(id, authToken!!)
        } catch (e: HttpException) {
            showToast(e.message)
            return
        } catch (e: IOException) {
            showToast(e.message)
            return
        }

        if (cAViewResponse.isSuccessful && cAViewResponse.body() != null) {
            val cAViewBody = cAViewResponse.body()!!.content
            setupSpinnerWithArray(
                caResponsiblePersonSpinner,
                R.array.responsible_person,
                cAViewBody.responsiblePersonName
            )
            setupSpinnerWithArray(
                hierarchyOfControlSpinner,
                R.array.assignCA,
                cAViewBody.hierarchyOfControl.value
            )
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

    private fun showToast(message: String?) {
        Toast.makeText(this@CAViewActivity, message, Toast.LENGTH_SHORT).show()
    }
}
