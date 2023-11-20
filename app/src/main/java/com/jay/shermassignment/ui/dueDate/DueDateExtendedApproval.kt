package com.jay.shermassignment.ui.dueDate

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.jay.shermassignment.R
import com.jay.shermassignment.dataModel.extenddate.DueDateExtension
import com.jay.shermassignment.dataModel.extenddate.ExtendDateBody
import com.jay.shermassignment.databinding.ActivityDueDateExtendedApprovalBinding
import com.jay.shermassignment.di.viewmodels.duedate.CheckDueDateViewModel
import com.jay.shermassignment.di.viewmodels.duedate.DueDateExtendReviewViewModel
import com.jay.shermassignment.generic.commonDateToISODate
import com.jay.shermassignment.generic.showConfirmationDialog
import com.jay.shermassignment.generic.showGenericDateDialog
import com.jay.shermassignment.generic.timestampToDate
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DueDateExtendedApproval : AppCompatActivity() {
    private lateinit var binding:ActivityDueDateExtendedApprovalBinding
    private lateinit var dueDateRequestViewModel: DueDateExtendReviewViewModel
    private lateinit var checkDueDateViewModel: CheckDueDateViewModel
    private var id:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDueDateExtendedApprovalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setTitle(R.string.due_date_extended_apporval)
        dueDateRequestViewModel=ViewModelProvider(this)[DueDateExtendReviewViewModel::class.java]
        checkDueDateViewModel=ViewModelProvider(this)[CheckDueDateViewModel::class.java]
        getDate()
        btClickListener()
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
           finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun btClickListener() {
        binding.btnApprove.setOnClickListener {
            approveData()
        }
        binding.btnReject.setOnClickListener {
            rejectData()
        }
        binding.llCalender.setOnClickListener {
            showGenericDateDialog(
                R.string.selectedDate.toString(),
                System.currentTimeMillis()
            ) { selectedDate ->
                val formattedDate =
                    SimpleDateFormat("dd-MM-yyyy", Locale.US).format(Date(selectedDate))
                binding.tvPreferredDateApproval.text = formattedDate
            }
        }
    }

    private fun getDate(){
        val correctiveActionId=intent.getIntExtra("cID",0)
        checkDueDateViewModel.dueDateApprove(correctiveActionId)
        checkDueDateViewModel._checkDueDateLiveData.observe(this){dueDateId->
            if(dueDateId?.isSuccess ==true){
                binding.tvPreferredDateApproval.text= timestampToDate(dueDateId.content.dueDateExtension.prefferedDate)
                id= dueDateId.content.dueDateExtension.id!!
            }

        }
    }

    private fun rejectData() {

        val dateTextValue = binding.tvPreferredDateApproval.text.toString()
        val commentText = binding.etmComment.text.toString()
        val correctiveActionId=intent.getIntExtra("cID",0)
        val date=commonDateToISODate(dateTextValue)
        val body = ExtendDateBody(
            correctiveActionId, DueDateExtension(commentText,id,
                date ,commentText,"")
        )

        dueDateRequestViewModel._dueDateRequestLiveData.observe(this){
            if(it?.isSuccess==true){
                showConfirmationDialog(getString(R.string.sherm),getString(R.string.reject_sucessfully)){
                    finish()
                }
            }
        }
        dueDateRequestViewModel.dueDateApproval("reject",body)
        dueDateRequestViewModel._errorMessageLiveData.observe(this){error->
            if(error != null){
                showConfirmationDialog(getString(R.string.error),error)
            }
        }

    }

    private fun approveData() {
        val correctiveActionId=intent.getIntExtra("cID",0)
        val dateTextValue = binding.tvPreferredDateApproval.text.toString()
        val commentText = binding.etmComment.text.toString()
        val date=commonDateToISODate(dateTextValue)
        val body = ExtendDateBody(
            correctiveActionId, DueDateExtension(commentText,id,date,commentText, status = "")
        )

        dueDateRequestViewModel.dueDateApproval("approve",body)
        dueDateRequestViewModel._dueDateRequestLiveData.observe(this){
            if(it?.isSuccess==true){
                showConfirmationDialog(getString(R.string.sherm),getString(R.string.approve_sucessfully)){
                    finish()
                }
            }
        }
        dueDateRequestViewModel._errorMessageLiveData.observe(this){error->
            if(error != null){
                showConfirmationDialog(getString(R.string.error),error)
            }
        }
    }
}