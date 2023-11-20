package com.jay.shermassignment.ui.add_inspection_completed

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.jay.shermassignment.R
import com.jay.shermassignment.dataModel.addinspectioncompletted.CompletedInspectionBody
import com.jay.shermassignment.databinding.ActivityAddInspectionCompletedBinding
import com.jay.shermassignment.di.viewmodels.inspectioncompleted.InspectionCompletedViewModel
import com.jay.shermassignment.generic.commonDateToISODate
import com.jay.shermassignment.generic.showConfirmationDialog
import com.jay.shermassignment.generic.showGenericDateDialog
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddInspectionCompleted : AppCompatActivity() {
    private lateinit var binding: ActivityAddInspectionCompletedBinding
    private lateinit var inspectionCompletedViewModel: InspectionCompletedViewModel
    private var inspectionId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddInspectionCompletedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setTitle(R.string.add_inspections_completed)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        inspectionCompletedViewModel =
            ViewModelProvider(this)[InspectionCompletedViewModel::class.java]
        buttonClickListener()
    }


    private fun buttonClickListener() {
        binding.btnCalenderInspectionCompleted.setOnClickListener {
            showGenericDateDialog(R.string.selectedDate.toString(), System.currentTimeMillis()) { selectedDate ->
                val formattedDate =
                    SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date(selectedDate))
                binding.tvInspectionCompletedDate.text = formattedDate
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->{
                setResult(RESULT_CANCELED)
            }
            R.id.save ->{
                saveData()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveData() {
        inspectionId= intent.getIntExtra("ids",0)
        val completedDate=binding.tvInspectionCompletedDate.text.toString()
        val body = CompletedInspectionBody(commonDateToISODate(completedDate),inspectionId)
        inspectionCompletedViewModel.dueDateApprove(body)
        inspectionCompletedViewModel._inspectiomCompletedLiveData.observe(this){completed->
            if(completed?.isSuccess ==true){
                showConfirmationDialog(getString(R.string.sucess),getString(R.string.complted)){
                    setResult(RESULT_OK)
                    finish()
                }
            }
        }
        inspectionCompletedViewModel._errorMessageLiveData.observe(this){error->
            if(error != null){
                showConfirmationDialog(getString(R.string.error),error)
            }
        }
    }
}