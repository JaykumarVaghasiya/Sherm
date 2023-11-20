package com.jay.shermassignment.ui.corrective_action_details

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.jay.shermassignment.R
import com.jay.shermassignment.databinding.ActivityCaviewBinding
import com.jay.shermassignment.di.viewmodels.correctiveaction.CorrectiveActionDetailsViewModel
import com.jay.shermassignment.generic.showConfirmationDialog
import com.jay.shermassignment.generic.timestampToDate
import com.jay.shermassignment.ui.approve_ca.ApproveCA
import com.jay.shermassignment.ui.dueDate.DueDateExtendRequest
import com.jay.shermassignment.ui.dueDate.DueDateExtendedApproval
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

class CAViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCaviewBinding
    private lateinit var correctiveActionDetailsViewModel: CorrectiveActionDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCaviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setTitle(R.string.caview)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        correctiveActionDetailsViewModel =
            ViewModelProvider(this)[CorrectiveActionDetailsViewModel::class.java]
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
        correctiveActionDetailsViewModel.getDueDate(id)
        correctiveActionDetailsViewModel._dueDateLiveData.observe(this){getDate->
            if(getDate?.isSuccess == true){
                val dueDate=getDate.content.dueDateExtension.id
                if(dueDate == null){
                    binding.btnDueDateApproval.visibility=View.GONE
                }
                val status=getDate.content.dueDateExtension.status
                if(status == "Approved" || status == "Rejected"){
                    binding.btnCApprove.visibility=View.VISIBLE
                    binding.btnDueDateApproval.visibility=View.GONE
                    binding.btnDueDateRequest.visibility=View.GONE
                }
            }
        }
    }

    private fun setClickListeners() {
        binding.btnDueDateRequest.setOnClickListener { navigateToDueDateExtendRequest() }
        binding.btnDueDateApproval.setOnClickListener { navigateToDueDateExtendedApproval() }
        binding.btnCApprove.setOnClickListener { navigateToApproveCA() }
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
        val id = intent.getIntExtra("correctiveActionId", 1)
        correctiveActionDetailsViewModel.addCorrectiveAction(id)
        correctiveActionDetailsViewModel._correctiveActionDetailsLiveData.observe(this) { cADetail ->
            if (cADetail?.isSuccess == true) {
                binding.etmCorrectiveAction.text = cADetail.content.action
                binding.spResponsiblePersonCA.text = cADetail.content.responsiblePersonName
                binding.spHierarchyOfControl.text = cADetail.content.hierarchyOfControl.value
                binding.spStatus.text = getStatus(cADetail.content.status)
                binding.tvDueDateCA.text = timestampToDate(cADetail.content.dueDate)
                binding.tvReviewDate.text = timestampToDate(cADetail.content.reviewDate)
                binding.tvFollowUp.text = getDatePeriod(
                    timestampToDate(cADetail.content.dueDate),
                    timestampToDate(cADetail.content.reviewDate)
                )
                if (binding.spStatus.text == "Closed") {
                    binding.btnDueDateApproval.visibility = View.GONE
                    binding.btnCApprove.visibility = View.VISIBLE
                    binding.btnDueDateRequest.visibility = View.GONE
                }
                binding.overLay.loadingScreen.visibility = View.GONE
            }
        }
        correctiveActionDetailsViewModel._errorMessageLiveData.observe(this) { error ->
            if (error != null) {
                showConfirmationDialog(getString(R.string.error), error)
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

