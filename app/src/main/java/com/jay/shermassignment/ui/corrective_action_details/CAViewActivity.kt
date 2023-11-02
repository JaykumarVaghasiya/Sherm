package com.jay.shermassignment.ui.corrective_action_details

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.jay.shermassignment.R
import com.jay.shermassignment.generic.showConfirmationDialog
import com.jay.shermassignment.generic.timestampToDate
import com.jay.shermassignment.ui.approve_ca.ApproveCA
import com.jay.shermassignment.ui.dueDate.DueDateExtendRequest
import com.jay.shermassignment.ui.dueDate.DueDateExtendedApproval
import com.jay.shermassignment.utils.SessionManager
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

class CAViewActivity : AppCompatActivity() {

    private lateinit var dueDateRequest: MaterialButton
    private lateinit var dueDateApproval: MaterialButton
    private lateinit var dueDateApproveCA: MaterialButton
    private lateinit var loading:LinearLayout
    private lateinit var dueDate: MaterialTextView
    private lateinit var reviewDate: MaterialTextView
    private lateinit var responsiblePerson: MaterialTextView
    private lateinit var hierarchyOfControl: MaterialTextView
    private lateinit var followUp: MaterialTextView
    private lateinit var status: MaterialTextView
    private lateinit var correctiveActionText: MaterialTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_caview)
        supportActionBar?.setTitle(R.string.caview)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initializeViews()
        getDate()
        fetchData()
        setClickListeners()
    }

    override fun onResume() {
        super.onResume()
        getDate()
        fetchData()
    }

    private fun getDate() {
        val id = intent.getIntExtra("correctiveActionId", 1)
        val authToken = SessionManager(this).fetchAuthToken()

        lifecycleScope.launch {
            val requestResponse = try {
                CAViewInstance.api.checkDueDateExtend(id, "Bearer $authToken")
            } catch (e: Exception) {
                showConfirmationDialog(getString(R.string.error), e.message)
                return@launch
            } catch (e: HttpException) {
                showConfirmationDialog(getString(R.string.error), e.message)
                return@launch
            } catch (e: IOException) {
                showConfirmationDialog(getString(R.string.error), e.message)
                return@launch
            }
            if (requestResponse.isSuccessful && requestResponse.body() != null) {
                val body = requestResponse.body()!!
                val dueDateId = body.content.dueDateExtension.id
                if (dueDateId == null) {
                    dueDateApproval.visibility = View.GONE
                }
                val status = body.content.dueDateExtension.status
                if (status == "Approved" || status == "Rejected") {
                    dueDateApproveCA.visibility = View.VISIBLE
                    dueDateApproval.visibility = View.GONE
                    dueDateRequest.visibility = View.GONE
                }
            }
        }
    }


    private fun initializeViews() {
        dueDateRequest = findViewById(R.id.btnDueDateRequest)
        dueDateApproval = findViewById(R.id.btnDueDateApproval)
        dueDateApproveCA=findViewById(R.id.btnCApprove)
        dueDate = findViewById(R.id.tvDueDateCA)
        reviewDate = findViewById(R.id.tvReviewDate)
        loading=findViewById(R.id.overLay)
        loading.bringToFront()
        loading.visibility=View.VISIBLE
        responsiblePerson = findViewById(R.id.spResponsiblePersonCA)
        hierarchyOfControl = findViewById(R.id.spHierarchyOfControl)
        status = findViewById(R.id.spStatus)
        correctiveActionText = findViewById(R.id.etmCorrectiveAction)
        followUp = findViewById(R.id.tvFollowUp)
    }


    private fun setClickListeners() {
        dueDateRequest.setOnClickListener { navigateToDueDateExtendRequest() }
        dueDateApproval.setOnClickListener { navigateToDueDateExtendedApproval() }
        dueDateApproveCA.setOnClickListener { navigateToApproveCA() }
    }

    private fun navigateToApproveCA() {
        val id = intent.getIntExtra("correctiveActionId", 1)
        val intent=Intent(this, ApproveCA::class.java)
        intent.putExtra("cID", id)
        startActivity(intent)
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
        val intent = Intent(this, DueDateExtendRequest::class.java)
        intent.putExtra("cID", id)
        startActivity(intent)
    }

    private fun navigateToDueDateExtendedApproval() {
        val id = intent.getIntExtra("correctiveActionId", 1)
        val intent = Intent(this, DueDateExtendedApproval::class.java)
        intent.putExtra("cID", id)
        startActivity(intent)
    }

    private fun fetchData() {
        val authToken = SessionManager(this).fetchAuthToken()
        val id = intent.getIntExtra("correctiveActionId", 1)
        lifecycleScope.launch {
            val cAViewResponse = try {
                CAViewInstance.api.getAllCAViewDetails(id, "Bearer $authToken")
            } catch (e: Exception) {
                showConfirmationDialog(getString(R.string.sherm), e.message)
                return@launch
            } catch (e: HttpException) {
                showConfirmationDialog(getString(R.string.sherm), e.message)
                return@launch
            } catch (e: IOException) {
                showConfirmationDialog(getString(R.string.sherm), e.message)
                return@launch
            }

            if (cAViewResponse.isSuccessful && cAViewResponse.body() != null) {
                val cAViewBody = cAViewResponse.body()!!.content
                correctiveActionText.text = cAViewBody.action
                responsiblePerson.text = cAViewBody.responsiblePersonName
                hierarchyOfControl.text = cAViewBody.hierarchyOfControl.value
                status.text = getStatus(cAViewBody.status)
                dueDate.text = timestampToDate(cAViewBody.dueDate)
                reviewDate.text = timestampToDate(cAViewBody.reviewDate)
                followUp.text = getDatePeriod(
                    timestampToDate(cAViewBody.dueDate),
                    timestampToDate(cAViewBody.reviewDate)
                )
                if (status.text == "Closed") {
                    dueDateApproval.visibility = View.GONE
                    dueDateRequest.visibility = View.GONE
                    dueDateApproveCA.visibility = View.VISIBLE
                }
                loading.visibility = View.GONE
            }
        }
    }

    private fun getStatus(siteSpinner: Int): String {
        return when (siteSpinner) {
            1 -> "Assigned"
            3 -> "Approved"
            4 -> "Rejected"
            else -> "Closed"
        }
    }

    private fun getDatePeriod(fromDate: String, toDate: String): String {
        val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

        val from = LocalDate.parse(fromDate, dateFormatter)
        val to = LocalDate.parse(toDate, dateFormatter)

        val period = Period.between(from, to)
        val months = period.months
        val days = period.days

        return if (months == 0 && days > 15) {
            "1 month"
        } else if (months > 0 && days > 15) {
            "${months + 1} months"
        } else {
            "$months months"
        }

    }
}

