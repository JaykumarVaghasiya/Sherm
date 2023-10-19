package com.jay.shermassignment.ui.inspectionDetailsUI

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.jay.shermassignment.R
import com.jay.shermassignment.generic.showGenericDateDialog
import com.jay.shermassignment.model.addInspectionData.InspectionCategoryMaster
import com.jay.shermassignment.model.addInspectionData.InspectionType
import com.jay.shermassignment.model.addInspectionData.ResponsiblePerson
import com.jay.shermassignment.model.addInspectionData.Site
import com.jay.shermassignment.model.addInspectionData.WorkplaceInspection
import com.jay.shermassignment.model.addInspectionData.addInspectionRef
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
    private lateinit var reportingTo: Spinner
    private lateinit var responsiblePersonSpinner: Spinner
    private lateinit var dueDate: MaterialTextView
    private lateinit var dateButton: MaterialButton
    private lateinit var status: Spinner


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_inspection_actitvtity)

        categorySpinner = findViewById(R.id.spCategory)
        inspectionTypeSpinner = findViewById(R.id.spInspectionType)
        inspectionLocationSpinner = findViewById(R.id.spInspectionLocation)
        site = findViewById(R.id.spSite)
        reportingTo = findViewById(R.id.spReportingTo)
        responsiblePersonSpinner = findViewById(R.id.spResponsiblePerson)
        dueDate = findViewById(R.id.tvDueDate)
        dateButton = findViewById(R.id.btCalender)
        status = findViewById(R.id.spStatus)



        spinnerValues()


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.save_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.save) {

            val authToken = SessionManager(this).fetchAuthToken()
            val category = categorySpinner.selectedItem.toString()
            val inspectionType = inspectionTypeSpinner.selectedItem.toString()
            val inspectionLocation = inspectionLocationSpinner.selectedItem.toString()
            val site = site.selectedItem.toString()
            val status = status.selectedItem
            val reportingTo = reportingTo.selectedItem.toString()
            val dueDate = dueDate.text.toString()
            val responsiblePerson = responsiblePersonSpinner.selectedItem.toString()

            lifecycleScope.launch {
                val addInspection = addInspectionRef(
                    id = null,
                    assignerId = 1,
                    reschedule = true,
                    inspectionLocation = inspectionLocation,
                    inspectionType = InspectionType(id = 425),
                    workplaceInspection = WorkplaceInspection(
                        id = 3500,
                        inspectionCategoryMaster = InspectionCategoryMaster(id = 10),
                        site = Site(id = 1)
                    ),
                    responsiblePerson = ResponsiblePerson(id = 102),
                    dueDate = dueDate
                )
                val addInspectionResponse = try {

                    ShowInspectionDetailsInstance.api.getAddInspectionData(
                        addInspection,
                        "Bearer $authToken"
                    )

                } catch (e: IOException) {
                    Toast.makeText(this@AddInspectionActivity, e.message, Toast.LENGTH_SHORT).show()
                    return@launch

                } catch (e: HttpException) {
                    Toast.makeText(this@AddInspectionActivity, e.message, Toast.LENGTH_SHORT).show()
                    return@launch
                }
                if (addInspectionResponse.isSuccessful && addInspectionResponse.body() != null) {
                    Toast.makeText(this@AddInspectionActivity, R.string.save, Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }
            }
            overridePendingTransition(
                com.google.android.material.R.anim.abc_grow_fade_in_from_bottom,
                R.anim.slide_out_to_left
            )
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
        setupSpinnerFromArray(reportingTo, R.array.responsible_person)
        setupSpinnerFromArray(responsiblePersonSpinner, R.array.responsible_person)
        setupSpinnerFromArray(status, R.array.status)
        dateButton.setOnClickListener {
            showGenericDateDialog(R.string.selectedDate.toString(), System.currentTimeMillis() , { selectedDate ->
                val formattedDate = SimpleDateFormat("dd-MM-yyyy", Locale.US).format(Date(selectedDate))
                dueDate.text = formattedDate
            }, this)
        }
    }


}

