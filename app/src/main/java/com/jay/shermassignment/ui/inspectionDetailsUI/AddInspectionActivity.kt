package com.jay.shermassignment.ui.inspectionDetailsUI

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.jay.shermassignment.R
import com.jay.shermassignment.dataModel.addInspectionData.AddInspectionRef
import com.jay.shermassignment.dataModel.addInspectionData.InspectionCategoryMaster
import com.jay.shermassignment.dataModel.addInspectionData.InspectionType
import com.jay.shermassignment.dataModel.addInspectionData.ResponsiblePerson
import com.jay.shermassignment.dataModel.addInspectionData.Site
import com.jay.shermassignment.dataModel.addInspectionData.WorkplaceInspection
import com.jay.shermassignment.databinding.ActivityAddInspectionActitvtityBinding
import com.jay.shermassignment.di.viewmodels.Inspection.AddInspectionViewModel
import com.jay.shermassignment.di.viewmodels.spinner.InspectionLocationViewModel
import com.jay.shermassignment.di.viewmodels.spinner.InspectionTypeViewModel
import com.jay.shermassignment.di.viewmodels.spinner.ReportingToViewModel
import com.jay.shermassignment.di.viewmodels.spinner.ResponsiblePersonViewModel
import com.jay.shermassignment.generic.commonDateToISODate
import com.jay.shermassignment.generic.setupSpinnerFromArray
import com.jay.shermassignment.generic.showConfirmationDialog
import com.jay.shermassignment.generic.showGenericDateDialog
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class AddInspectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddInspectionActitvtityBinding
    private lateinit var inspectionTypeViewModel: InspectionTypeViewModel
    private lateinit var inspectionLocationViewModel: InspectionLocationViewModel
    private lateinit var reportingToViewModel: ReportingToViewModel
    private lateinit var responsiblePersonViewModel: ResponsiblePersonViewModel
    private lateinit var addInspectionViewModel: AddInspectionViewModel
    private var categoryId: Int = 0
    private var inspectionTypeId: Int = 0
    private var siteId: Int = 0
    private var responsiblePersonId: Int = 0
    private var inspectionLocationName: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddInspectionActitvtityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setTitle(R.string.schedule_inspection)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initializeViewModel()
        spinnerValues()
        onViewClick()
        btClickListener()
    }

    private fun initializeViewModel() {
        inspectionTypeViewModel = ViewModelProvider(this)[InspectionTypeViewModel::class.java]
        inspectionLocationViewModel =
            ViewModelProvider(this)[InspectionLocationViewModel::class.java]
        reportingToViewModel = ViewModelProvider(this)[ReportingToViewModel::class.java]
        responsiblePersonViewModel = ViewModelProvider(this)[ResponsiblePersonViewModel::class.java]
        addInspectionViewModel = ViewModelProvider(this)[AddInspectionViewModel::class.java]
    }


    private fun btClickListener() {
        binding.btCalender.setOnClickListener {
            showGenericDateDialog(
                getString(R.string.selectedDate),
                System.currentTimeMillis(),
            ) { selectedDate ->
                val formattedDate =
                    SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date(selectedDate))
                binding.tvDueDate.text = formattedDate
            }
        }
    }

    private fun onViewClick() {
        binding.spCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                binding.overLay.loadingScreen.visibility = View.VISIBLE
                getInspectionType()
                getInspectionLocation()
                onInspectionType()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        binding.spInspectionType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    getInspectionLocation()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
        binding.spSite.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                getInspectionLocation()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }


    }
    private fun spinnerValues() {
        setupSpinnerFromArray(binding.spCategory, R.array.category)
    }


    private fun getCategoryValue(categorySpinner: String): Int {
        return when (categorySpinner) {
            "Work Health and Safety" -> 10
            else -> 0
        }
    }

    private fun getInspectionType() {
        categoryId = getCategoryValue(binding.spCategory.selectedItem.toString())
        inspectionTypeViewModel.inspectionType(categoryId)
        inspectionTypeViewModel._InspectionTypeLiveData.observe(this) {
            val inspectionNames = it?.content?.map { inspectionType ->
                inspectionType.name
            }
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                inspectionNames ?: emptyList()
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spInspectionType.adapter = adapter
            binding.spInspectionType.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?, view: View?, position: Int, id: Long
                    ) {
                        inspectionTypeId = it?.content?.get(position)?.id ?: 0
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }
                }
        }

        inspectionTypeViewModel._errorMessageLiveData.observe(this) { error ->
            showConfirmationDialog(getString(R.string.error), error)
        }
    }


    private fun onInspectionType() {
        binding.spSite.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                getInspectionLocation()
                binding.overLay.loadingScreen.visibility = View.VISIBLE
                getResponsiblePerson()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun getSiteValue(siteSpinner: String): Int {
        return when (siteSpinner) {
            "Client Site" -> 18
            "Wynnum" -> 1
            else -> 0
        }
    }


    private fun getInspectionLocation() {
        categoryId = getCategoryValue(binding.spCategory.selectedItem.toString())
        siteId = getSiteValue(binding.spSite.selectedItem.toString())

        inspectionLocationViewModel.inspectionLocation(categoryId, siteId, inspectionTypeId)
        inspectionLocationViewModel._InspectionLocationLiveData.observe(this) { body ->
            val location = body?.content?.map {
                it.inspectionLocation
            }
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                location ?: emptyList()
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spInspectionLocation.adapter = adapter
            binding.spInspectionLocation.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?, view: View?, position: Int, id: Long
                    ) {
                        inspectionLocationName =
                            body?.content?.get(position)?.inspectionLocation ?: ""
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
        }
        inspectionLocationViewModel._errorMessageLiveData.observe(this) { error ->
            showConfirmationDialog(getString(R.string.error), error)
        }
    }

    private fun getResponsiblePerson() {
        responsiblePersonViewModel.responsiblePerson(siteId)
        responsiblePersonViewModel._responsiblePersonLiveData.observe(this) { body ->
            val responsiblePerson = body?.content?.map {
                it.fullName
            }
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                responsiblePerson ?: emptyList()
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spResponsiblePerson.adapter = adapter
            binding.spResponsiblePerson.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?, view: View?, position: Int, id: Long
                    ) {
                        responsiblePersonId = body?.content?.get(position)?.id ?: 0
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
        }
        responsiblePersonViewModel._errorMessageLiveData.observe(this) { error ->
            showConfirmationDialog(getString(R.string.error), error)
        }
    }

    private fun getRescheduleValue(rescheduleSpinner: String): Boolean? {
        return when (rescheduleSpinner) {
            "Yes" -> true
            "No" -> false
            else -> null
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
            saveInspectionData()
        }
        if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun saveInspectionData() {
        val category = binding.spCategory.selectedItem.toString()
        val dueDate = commonDateToISODate(binding.tvDueDate.text.toString())
        val reschedule = binding.spReschedule.selectedItem.toString()
        categoryId = getCategoryValue(category)
        getRescheduleValue(reschedule)

        addInspectionViewModel.addInspection(
            addInspectionRef = AddInspectionRef(
                assignerId = responsiblePersonId,
                reschedule = true,
                inspectionLocation = inspectionLocationName,
                workplaceInspection = WorkplaceInspection(
                    id = 3500,
                    inspectionCategoryMaster = InspectionCategoryMaster(categoryId),
                    site = Site(siteId)
                ),
                responsiblePerson = ResponsiblePerson(responsiblePersonId),
                dueDate = dueDate,
                inspectionType = InspectionType(inspectionTypeId)
            )
        )

        addInspectionViewModel._addInspectionLiveData.observe(this) { response ->
            if (response?.isSuccess == true) {
                showConfirmationDialog(
                    getString(R.string.sucess),
                    getString(R.string.saved_sucessfully)
                ){
                    finish()
                }
            }
        }
        addInspectionViewModel._errorMessageLiveData.observe(this) { error ->
            if (error != null) {
                showConfirmationDialog(getString(R.string.error), error)
            }
        }
    }
}
