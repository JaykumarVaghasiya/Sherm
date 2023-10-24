package com.jay.shermassignment.ui.inspectionDetailsUI

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.jay.shermassignment.R
import com.jay.shermassignment.generic.BackCallBack
import com.jay.shermassignment.generic.showGenericDateDialog
import com.jay.shermassignment.generic.showToast
import com.jay.shermassignment.response.addInspectionData.InspectionCategoryMaster
import com.jay.shermassignment.response.addInspectionData.InspectionType
import com.jay.shermassignment.response.addInspectionData.ResponsiblePerson
import com.jay.shermassignment.response.addInspectionData.Site
import com.jay.shermassignment.response.addInspectionData.WorkplaceInspection
import com.jay.shermassignment.response.addInspectionData.addInspectionRef
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_inspection_actitvtity)
        supportActionBar?.setTitle(R.string.add_inspections_completed)
        initializeView()
        spinnerValues()
        btClickListener()
        backBtListener()
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
                R.string.selectedDate.toString(),
                System.currentTimeMillis(),
            ) { selectedDate ->
                val formattedDate =
                    SimpleDateFormat("dd-MM-yyyy", Locale.US).format(Date(selectedDate))
                dueDate.text = formattedDate
            }
        }
    }

    private fun backBtListener() {
        val onBackPressedCallback = BackCallBack {
            finish()
        }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
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
        return super.onOptionsItemSelected(item)
    }

    private fun setupSpinnerFromArray(spinner: Spinner, arrayResId: Int) {
        ArrayAdapter.createFromResource(this, arrayResId, android.R.layout.simple_spinner_item)
            .also { arrayAdapter ->
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = arrayAdapter
            }
    }

    private fun spinnerValues() {
        setupSpinnerFromArray(categorySpinner, R.array.category)
        setupSpinnerFromArray(inspectionTypeSpinner, R.array.category_work_health_and_safety)
        setupSpinnerFromArray(site, R.array.site)
        setupSpinnerFromArray(inspectionLocationSpinner, R.array.inspection_location)
        setupSpinnerFromArray(reschedule, R.array.reschedule)
        setupSpinnerFromArray(responsiblePersonSpinner, R.array.responsible_person)
    }

    private fun saveInspectionData() {
        val authToken = SessionManager(this).fetchAuthToken()
        val category = categorySpinner.selectedItem.toString()
        val inspectionType = inspectionTypeSpinner.selectedItem.toString()
        val inspectionLocation = inspectionLocationSpinner.selectedItem.toString()
        val site = site.selectedItem.toString()
        val dueDate = dueDate.text.toString()
        val responsiblePerson = responsiblePersonSpinner.selectedItem.toString()
        val reschedule = reschedule.selectedItem.toString()

        val categoryValue = getCategoryValue(category)
        val inspectionTypeValue = getInspectionType(inspectionType)
        val responsiblePersonType = getResponsiblePerson(responsiblePerson)
        val reScheduleValue = getRescheduleValue(reschedule)
        val siteValue = getSiteValue(site)
        val assignerValue = getAssigner(responsiblePerson)

        val addInspection = addInspectionRef(
            id = null,
            assignerId = responsiblePersonType,
            reschedule = reScheduleValue!!,
            inspectionLocation = inspectionLocation,
            inspectionType = InspectionType(inspectionTypeValue),
            workplaceInspection = WorkplaceInspection(
                id = 3500,
                inspectionCategoryMaster = InspectionCategoryMaster(categoryValue),
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

    private fun getCategoryValue(categorySpinner: String): Int{
        return when (categorySpinner) {
            "Work Health and Safety" -> 10
            else -> 0
        }
    }

    private fun getInspectionType(inspectionTypeSpinner: String): Int {
        return when (inspectionTypeSpinner) {
            "Vehicle Pre-Start" -> 425
            "Daily Pre-Start Checklist Inspection(Inline)" -> 626
            "Site Workplace Inspection" -> 655
            "Take 5" -> 607
            else -> 0
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
