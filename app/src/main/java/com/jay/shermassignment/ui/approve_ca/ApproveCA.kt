package com.jay.shermassignment.ui.approve_ca

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.jay.shermassignment.R
import com.jay.shermassignment.dataModel.approveca.ApproveCABody
import com.jay.shermassignment.databinding.ActivityApproveCaBinding
import com.jay.shermassignment.di.viewmodels.duedate.DueDateApprovalViewModel
import com.jay.shermassignment.generic.showConfirmationDialog

class ApproveCA : AppCompatActivity() {

    private lateinit var binding:ActivityApproveCaBinding
    private lateinit var dueDateApprovalViewModel: DueDateApprovalViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityApproveCaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.approverejectCA)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        dueDateApprovalViewModel=ViewModelProvider(this)[DueDateApprovalViewModel::class.java]
        buttonClickListener()
    }

    private fun buttonClickListener() {
        binding.btnApproveCA.setOnClickListener {
            approveData()
        }
        binding.btnRejectCA.setOnClickListener {
            rejectData()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun rejectData() {

        val commentText = binding.etmCommentCA.text.toString()
        val status = "rejected"
        val id=intent.getIntExtra("cID",1)
        val body = ApproveCABody(
            status,commentText,id
        )
        dueDateApprovalViewModel.dueDateReject(body)
        dueDateApprovalViewModel._dueDateLiveData.observe(this){rejected->
            if(rejected?.isSuccess ==true){
                showConfirmationDialog(getString(R.string.sucess),getString(R.string.reject_sucessfully)){
                    finish()
                }
            }
        }
        dueDateApprovalViewModel._errorMessageLiveData.observe(this){error->
            if(error != null){
                showConfirmationDialog(getString(R.string.error),error)
            }
        }
    }

    private fun approveData() {

        val commentText = binding.etmCommentCA.text.toString()
        val status = "approved"
        val id=intent.getIntExtra("cID",1)
        val body = ApproveCABody(
           status,commentText,id
        )
        dueDateApprovalViewModel.dueDateApprove(body)
        dueDateApprovalViewModel._dueDateLiveData.observe(this){approved->
            if(approved?.isSuccess==true){
                showConfirmationDialog(getString(R.string.sucess),getString(R.string.approve_sucessfully)){
                    finish()
                }
            }
        }
        dueDateApprovalViewModel._errorMessageLiveData.observe(this){error->
            if(error != null){
                showConfirmationDialog(getString(R.string.error),error)
            }
        }
    }
}