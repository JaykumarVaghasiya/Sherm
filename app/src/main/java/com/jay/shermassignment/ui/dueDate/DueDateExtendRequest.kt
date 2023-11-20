package com.jay.shermassignment.ui.dueDate

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.jay.shermassignment.R
import com.jay.shermassignment.dataModel.extenddate.DueDateExtension
import com.jay.shermassignment.dataModel.extenddate.ExtendDateBody
import com.jay.shermassignment.databinding.ActivityDueDateExtendRequestBinding
import com.jay.shermassignment.di.viewmodels.duedate.DueDateRequestViewModel
import com.jay.shermassignment.generic.commonDateToISODate
import com.jay.shermassignment.generic.showConfirmationDialog
import com.jay.shermassignment.generic.showGenericDateDialog
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DueDateExtendRequest : AppCompatActivity() {

    private lateinit var binding: ActivityDueDateExtendRequestBinding
    private lateinit var dueDateRequestViewModel: DueDateRequestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDueDateExtendRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setTitle(R.string.due_date_extended_request)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        dueDateRequestViewModel=ViewModelProvider(this)[DueDateRequestViewModel::class.java]
        btnClickListener()
    }

    private fun btnClickListener() {
        binding.btnPreferredDate.setOnClickListener {
            showGenericDateDialog(
                getString(R.string.selectedDate),
                System.currentTimeMillis()
            ) { selectedDate ->
                val formattedDate =
                    SimpleDateFormat("dd-MM-yyyy", Locale.US).format(Date(selectedDate))
                binding.tvPreferredDate.text = formattedDate
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.save_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.save) {
            sendDueDateRequest()
        }
        if(id == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun sendDueDateRequest() {
        val id = intent.getIntExtra("cID", 0)
        val comment = binding.etComments.text.toString()
        val preferredDate = binding.tvPreferredDate.text.toString()
        val body = ExtendDateBody(
            id,
            DueDateExtension(
                comment,
                null,
                commonDateToISODate(preferredDate),
                comment,
                status = ""
            )
        )
        dueDateRequestViewModel.addInspection("request",body)
        dueDateRequestViewModel._dueDateRequestLiveData.observe(this){extendDate->
            if(extendDate?.isSuccess ==true){
                showConfirmationDialog(getString(R.string.sucess),getString(R.string.due_date_extended_request))
                {
                    finish()
                }
            }
        }
        dueDateRequestViewModel._errorMessageLiveData.observe(this){error->
            showConfirmationDialog(getString(R.string.error),error)
        }
    }
}