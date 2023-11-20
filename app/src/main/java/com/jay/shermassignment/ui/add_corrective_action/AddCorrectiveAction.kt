package com.jay.shermassignment.ui.add_corrective_action

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.jay.shermassignment.R
import com.jay.shermassignment.databinding.ActivityAssignCorrectiveActionBinding
import com.jay.shermassignment.di.viewmodels.correctiveaction.AddCorrectiveActionViewModel
import com.jay.shermassignment.di.viewmodels.spinner.ReportingToViewModel
import com.jay.shermassignment.generic.commonDateToISODate
import com.jay.shermassignment.generic.setupSpinnerFromArray
import com.jay.shermassignment.generic.showConfirmationDialog
import com.jay.shermassignment.generic.showGenericDateDialog
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AddCorrectiveAction : AppCompatActivity() {

    private lateinit var binding: ActivityAssignCorrectiveActionBinding
    private lateinit var addCorrectiveActionViewModel: AddCorrectiveActionViewModel
    private lateinit var reportingToViewModel: ReportingToViewModel
    private var responsibleId: Int = 0
    private var inspectionScheduleId: Int = 0
    private var selectedDueDate: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssignCorrectiveActionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setTitle(R.string.assign_ca)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        reportingToViewModel = ViewModelProvider(this)[ReportingToViewModel::class.java]
        addCorrectiveActionViewModel =
            ViewModelProvider(this)[AddCorrectiveActionViewModel::class.java]
        spinnerValue()
        getAllResponsiblePerson()
        btClickListener()
        spinnerValue()
    }

    private fun getAllResponsiblePerson() {

        reportingToViewModel.reportingTo()
        reportingToViewModel._reportingToLiveData.observe(this) { reportingTo ->
            val responsiblePersonName = reportingTo?.content?.map {
                it.fullName
            }
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                this@AddCorrectiveAction,
                android.R.layout.simple_spinner_item,
                responsiblePersonName ?: emptyList()
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spResponsiblePersonAddCA.adapter = adapter
            binding.spResponsiblePersonAddCA.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        responsibleId = reportingTo?.content?.get(position)?.id ?: 0
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }
                }
        }
    }


    private fun spinnerValue() {
        setupSpinnerFromArray(binding.spHierarchyOfControlAddCA, R.array.hierarchyControl)
        setupSpinnerFromArray(binding.spFollowUpAddCA, R.array.followUp)
        setupSpinnerFromArray(binding.spStatusAddCA, R.array.status)
    }


    private fun gatherData() {

        val hierarchyOfControl = binding.spHierarchyOfControlAddCA.selectedItem.toString()
        val hierarchyOfControlValue = getHierarchyControl(hierarchyOfControl)
        val correctiveAction = binding.etmAddCA.text.toString()
        val dueDateText = binding.tvDueDateAddCA.text.toString()
        val reviewDateValue = binding.tvReviewDateAddCA.text.toString()
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
        addCorrectiveActionViewModel.addCorrectiveAction(body)
        addCorrectiveActionViewModel._addCorrectiveActionLiveData.observe(this){response->
            if(response?.isSuccess == true){
                showConfirmationDialog(getString(R.string.sucess),getString(R.string.saved_sucessfully)){
                    finish()
                }
            }
        }
        addCorrectiveActionViewModel._errorMessageLiveData.observe(this){
            if(it != null){
                showConfirmationDialog(getString(R.string.error),it)
            }
        }
    }

    private fun btClickListener() {
        binding.btnDuedateAddCA.setOnClickListener {
            showGenericDateDialog(
                getString(R.string.selectedDate),
                System.currentTimeMillis()
            ) { selectedDate ->
                selectedDueDate = selectedDate
                val formattedDate =
                    SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date(selectedDate))
                binding.tvDueDateAddCA.text = formattedDate

                // Calculate and display the review date based on the selected follow-up duration

                calculateReviewDate()
            }
        }
        binding.spFollowUpAddCA.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
        val selectedFollowUpItem = binding.spFollowUpAddCA.selectedItem.toString()
        val followUpMonths = selectedFollowUpItem.split(" ")[0].toInt()

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = selectedDueDate
        calendar.add(Calendar.MONTH, followUpMonths)

        val formattedReviewDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(calendar.time)
        binding.tvReviewDateAddCA.text = formattedReviewDate
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> {
                binding.overLay.loadingScreen.visibility=View.VISIBLE
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