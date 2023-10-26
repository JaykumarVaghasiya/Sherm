package com.jay.shermassignment.ui.inspectionDetailsUI

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.jay.shermassignment.R
import com.jay.shermassignment.api.inspection.SpinnerInstance
import com.jay.shermassignment.generic.setupSpinnerFromArray
import com.jay.shermassignment.generic.showGenericDateDialog
import com.jay.shermassignment.generic.showToast
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
    private var categoryId: Int = 0
    private var inspectionTypeId: Int = 0
    private var siteId: Int = 0
    private var responsiblePersonId: Int = 0
    private var inspectionId: Int = 0
    private var authToken: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_inspection_actitvtity)
        supportActionBar?.setTitle(R.string.add_inspections_completed)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        authToken = "Bearer ${SessionManager(this).fetchAuthToken()}"
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
    }

    private fun btClickListener() {
        dateButton.setOnClickListener {
            showGenericDateDialog(
                getString(R.string.selectedDate),
                System.currentTimeMillis(),
            ) { selectedDate ->
                val formattedDate =
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(selectedDate))
                dueDate.text = formattedDate
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
            saveInspectionData()
            finish()
        }
        if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun onViewClick(){
        categorySpinner.onItemSelectedListener =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                getInspectionType()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }


    private fun spinnerValues() {
        setupSpinnerFromArray(categorySpinner, R.array.category)

    }

    private fun saveInspectionData() {
        val authToken = SessionManager(this).fetchAuthToken()
        val category = categorySpinner.selectedItem.toString()
        val inspectionType = inspectionTypeSpinner.selectedItem.toString()
        val site = site.selectedItem.toString()
        val dueDate = dueDate.text.toString()
        val responsiblePerson = responsiblePersonSpinner.selectedItem.toString()
        val reschedule = reschedule.selectedItem.toString()

        categoryId = getCategoryValue(category)
        val responsiblePersonType = getResponsiblePerson(responsiblePerson)
        getRescheduleValue(reschedule)
        val siteValue = getSiteValue(site)
        val assignerValue = getAssigner(responsiblePerson)

        val addInspection = AddInspectionRef(
            assignerId = responsiblePersonType,
            reschedule = true,
            inspectionLocation = "Subaru Outback",
            inspectionType = InspectionType(inspectionId),
            workplaceInspection = WorkplaceInspection(
                id = 3500,
                inspectionCategoryMaster = InspectionCategoryMaster(categoryId),
                site = Site(siteValue)
            ),
            responsiblePerson = ResponsiblePerson(assignerValue),
            dueDate = dueDate
        )


        lifecycleScope.launch {
            val addInspectionResponse = try {
                ShowInspectionDetailsInstance.api.getAddInspectionData(
                    addInspection,
                    "Bearer $authToken"
                )
            } catch (e: Exception) {
                showToast( e.message)
                return@launch
            } catch (e: HttpException) {
                showToast( e.message)
                return@launch
            } catch (e: IOException) {
                showToast( e.message)
                return@launch
            }
            if (addInspectionResponse.isSuccessful && addInspectionResponse.body() != null) {
                showToast( getString(R.string.save))
                finish()
            } else {
                showToast( getString(R.string.failed_to_save))
            }
        }
    }

    private fun getCategoryValue(categorySpinner: String): Int {
        return when (categorySpinner) {
            "Work Health and Safety" -> 10
            else -> 0
        }
    }

    private fun getInspectionType() {
        lifecycleScope.launch {
            categoryId=getCategoryValue(categorySpinner.selectedItem.toString())
            val inspectionTypeResponse = try {
                SpinnerInstance.api.getInspectionTypeFromCategory(categoryId, authToken)
            } catch (e: Exception) {
                showToast(e.message)
                return@launch
            } catch (e: HttpException) {
                showToast(e.message)
                return@launch
            } catch (e: IOException) {
                showToast(e.message)
                return@launch
            }
            if (inspectionTypeResponse.isSuccessful && inspectionTypeResponse.body() != null) {
                val body = inspectionTypeResponse.body()

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
                            showToast("{$inspectionTypeId}")
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }
                    }

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

    private fun getResponsiblePerson(responsibleSpinner: String): Int {
        return when (responsibleSpinner) {
            "Caroline Kingston" -> 1
            "Test Supervisor" -> 2
            "Test Manager" -> 3
            "Test Employee" -> 248
            else -> 0
        }
    }

    private fun getAssigner(responsibleSpinner: String): Int {
        return when (responsibleSpinner) {
            "Caroline Kingston" -> 1
            "Test Supervisor" -> 2
            "Test Manager" -> 3
            "Test Employee" -> 248
            else -> 0
        }
    }

    private fun getRescheduleValue(rescheduleSpinner: String): Boolean? {
        return when (rescheduleSpinner) {
            "Yes" -> true
            "No" -> false
            else -> null
        }
    }
}
