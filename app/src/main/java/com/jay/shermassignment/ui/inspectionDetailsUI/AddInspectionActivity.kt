package com.jay.shermassignment.ui.inspectionDetailsUI

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.jay.shermassignment.R
import com.jay.shermassignment.api.inspection.SpinnerInstance
import com.jay.shermassignment.generic.commonDateToISODate
import com.jay.shermassignment.generic.setupSpinnerFromArray
import com.jay.shermassignment.generic.showConfirmationDialog
import com.jay.shermassignment.generic.showGenericDateDialog
import com.jay.shermassignment.response.addInspectionData.AddInspectionRef
import com.jay.shermassignment.response.addInspectionData.InspectionCategoryMaster
import com.jay.shermassignment.response.addInspectionData.InspectionType
import com.jay.shermassignment.response.addInspectionData.ResponsiblePerson
import com.jay.shermassignment.response.addInspectionData.Site
import com.jay.shermassignment.response.addInspectionData.WorkplaceInspection
import com.jay.shermassignment.utils.SessionManager
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class AddInspectionActivity : AppCompatActivity() {

    private lateinit var categorySpinner: Spinner
    private lateinit var inspectionTypeSpinner: Spinner
    private lateinit var inspectionLocationSpinner: Spinner
    private lateinit var site: Spinner
    private lateinit var reschedule: Spinner
    private lateinit var responsiblePersonSpinner: Spinner
    private lateinit var dueDate: MaterialTextView
    private lateinit var dateButton: MaterialButton
    private lateinit var loading: LinearLayout
    private var categoryId: Int = 0
    private var inspectionTypeId: Int = 0
    private var siteId: Int = 0
    private var responsiblePersonId: Int = 0
    private var inspectionLocationName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_inspection_actitvtity)
        supportActionBar?.setTitle(R.string.schedule_inspection)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initializeView()
        spinnerValues()
        onViewClick()
        btClickListener()
    }
    private fun initializeView() {
        categorySpinner = findViewById(R.id.spCategory)
        inspectionTypeSpinner = findViewById(R.id.spInspectionType)
        inspectionLocationSpinner = findViewById(R.id.spInspectionLocation)
        site = findViewById(R.id.spSite)
        responsiblePersonSpinner = findViewById(R.id.spResponsiblePerson)
        dueDate = findViewById(R.id.tvDueDate)
        dateButton = findViewById(R.id.btCalender)
        reschedule = findViewById(R.id.spReschedule)
        loading=findViewById(R.id.overLay)
        loading.bringToFront()
        loading.visibility = View.GONE
    }

    private fun btClickListener() {
        dateButton.setOnClickListener {
            showGenericDateDialog(
                getString(R.string.selectedDate),
                System.currentTimeMillis(),
            ) { selectedDate ->
                val formattedDate =
                    SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date(selectedDate))
                dueDate.text = formattedDate
            }
        }
    }

    private fun onViewClick() {
        categorySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    loading.visibility = View.VISIBLE
                    getInspectionType()
                    getInspectionLocation()
                    onInspectionType()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
        inspectionTypeSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    getInspectionLocation()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }

    }
    private fun spinnerValues() {
        setupSpinnerFromArray(categorySpinner, R.array.category)
    }


    private fun getCategoryValue(categorySpinner: String): Int {
        return when (categorySpinner) {
            "Work Health and Safety" -> 10
            else -> 0
        }
    }

    private fun getInspectionType() {
        val authToken = SessionManager(this).fetchAuthToken()

        lifecycleScope.launch {
            categoryId=getCategoryValue(categorySpinner.selectedItem.toString())
            val inspectionTypeResponse = try {
                SpinnerInstance.api.getInspectionTypeFromCategory(categoryId, "Bearer $authToken")
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
            if (inspectionTypeResponse.isSuccessful && inspectionTypeResponse.body() != null) {
                val body = inspectionTypeResponse.body()
                loading.visibility = View.GONE
                val inspectionNames = body?.content?.map { inspection ->
                    inspection.name
                }
                val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                    this@AddInspectionActivity,
                    android.R.layout.simple_spinner_item,
                    inspectionNames ?: emptyList()
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                inspectionTypeSpinner.adapter = adapter
                inspectionTypeSpinner.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            inspectionTypeId = body?.content?.get(position)?.id ?: 0
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }
                    }
            }
        }
    }

    private fun onInspectionType() {
        site.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                getInspectionLocation()
                loading.visibility = View.VISIBLE
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
        val authToken = SessionManager(this).fetchAuthToken()

        lifecycleScope.launch {
            categoryId = getCategoryValue(categorySpinner.selectedItem.toString())
            siteId = getSiteValue(site.selectedItem.toString())
            val inspectionLocationResponse = try {
                SpinnerInstance.api.getLocationFromCatInsTypeSite(
                    categoryId,
                    siteId,
                    inspectionTypeId,
                    "Bearer $authToken"
                )
            } catch (e: Exception) {
                showConfirmationDialog(getString(R.string.sherm),e.message)
                return@launch
            } catch (e: HttpException) {
                showConfirmationDialog(getString(R.string.sherm),e.message)
                return@launch
            } catch (e: IOException) {
                showConfirmationDialog(getString(R.string.sherm),e.message)
                return@launch
            }
            if (inspectionLocationResponse.isSuccessful && inspectionLocationResponse.body() != null) {
                val body = inspectionLocationResponse.body()
                loading.visibility = View.GONE
                val inspectionLocation = body?.content?.map { location ->
                    location.inspectionLocation
                }
                val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                    this@AddInspectionActivity,
                    android.R.layout.simple_spinner_item,
                    inspectionLocation ?: emptyList()
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                inspectionLocationSpinner.adapter = adapter
                inspectionLocationSpinner.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            inspectionLocationName =
                                body?.content?.get(position)?.inspectionLocation ?: ""
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                        }
                    }
            }
        }
    }

    private fun getResponsiblePerson() {
        val authToken = SessionManager(this).fetchAuthToken()
        lifecycleScope.launch {
            val responsiblePersonResponse = try {
                SpinnerInstance.api.getResponsiblePersonBySite(siteId, "Bearer $authToken")
            } catch (e: Exception) {
                showConfirmationDialog(getString(R.string.sherm),e.message)
                return@launch
            } catch (e: HttpException) {
                showConfirmationDialog(getString(R.string.sherm),e.message)
                return@launch
            } catch (e: IOException) {
                showConfirmationDialog(getString(R.string.sherm),e.message)
                return@launch
            }
            if (responsiblePersonResponse.isSuccessful && responsiblePersonResponse.body() != null) {
                val body = responsiblePersonResponse.body()
                loading.visibility = View.GONE
                val inspectionNames = body?.content?.map {
                    it.fullName
                }
                val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                    this@AddInspectionActivity,
                    android.R.layout.simple_spinner_item,
                    inspectionNames ?: emptyList()
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                responsiblePersonSpinner.adapter = adapter
                responsiblePersonSpinner.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            responsiblePersonId = body?.content?.get(position)?.id ?: 0
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                        }
                    }
            }
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
        val authToken = SessionManager(this).fetchAuthToken()
        val category = categorySpinner.selectedItem.toString()
        val dueDate = commonDateToISODate(dueDate.text.toString())
        val reschedule = reschedule.selectedItem.toString()
        categoryId = getCategoryValue(category)
        getRescheduleValue(reschedule)

        val addInspection = AddInspectionRef(
            assignerId = responsiblePersonId,
            reschedule = true,
            inspectionLocation = inspectionLocationName,
            inspectionType = InspectionType(inspectionTypeId),
            workplaceInspection = WorkplaceInspection(
                id = 3500,
                inspectionCategoryMaster = InspectionCategoryMaster(categoryId),
                site = Site(siteId)
            ),
            responsiblePerson = ResponsiblePerson(responsiblePersonId),
            dueDate = dueDate
        )
        lifecycleScope.launch {
            val addInspectionResponse = try {
                ShowInspectionDetailsInstance.api.getAddInspectionData(
                    addInspection,
                    "Bearer $authToken"
                )
            } catch (e: Exception) {
                showConfirmationDialog(getString(R.string.sherm),e.message)
                return@launch
            } catch (e: HttpException) {
                showConfirmationDialog(getString(R.string.sherm),e.message)
                return@launch
            } catch (e: IOException) {
                showConfirmationDialog(getString(R.string.sherm),e.message)
                return@launch
            }
            if (addInspectionResponse.isSuccessful && addInspectionResponse.body() != null) {
                showConfirmationDialog(getString(R.string.sucess),getString(R.string.added_insp))
                finish()
            } else {
                showConfirmationDialog(getString(R.string.failed),getString(R.string.failed_to_save))
            }
        }
    }
}
