package com.jay.shermassignment.ui.inspectionDetailsUI

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textview.MaterialTextView
import com.jay.shermassignment.CorrectiveAction
import com.jay.shermassignment.R
import com.jay.shermassignment.ui.commentUI.Comment
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ShowInspectionDetailsActivity : AppCompatActivity() {

    private lateinit var correctiveAction: MaterialButton
    private lateinit var comment: MaterialButton
    private lateinit var docUpload: MaterialButton

    private lateinit var category: MaterialTextView
    private lateinit var categoryList: Spinner

    private lateinit var inspectionType: MaterialTextView
    private lateinit var inspectionTypeList: Spinner

    private lateinit var site: MaterialTextView
    private lateinit var siteText: EditText

    private lateinit var inspectionLocation: MaterialTextView
    private lateinit var inspectionLocationList: Spinner

    private lateinit var responsiblePerson: MaterialTextView
    private lateinit var responsiblePersonList: Spinner

    private lateinit var dueDate: MaterialTextView
    private lateinit var dateButton: MaterialButton

    private lateinit var reportingTo: MaterialTextView
    private lateinit var reportingToList: Spinner

    private lateinit var linearLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_inspection_details2)

        category = findViewById(R.id.tvCategory)
        categoryList = findViewById(R.id.spCategory)

        inspectionType = findViewById(R.id.tvInspectionType)
        inspectionTypeList = findViewById(R.id.spInspectionType)

        site = findViewById(R.id.tvSite)
        siteText = findViewById(R.id.etSite)

        inspectionLocation = findViewById(R.id.tvInspectionLocation)
        inspectionLocationList = findViewById(R.id.spInspectionLocation)

        responsiblePerson = findViewById(R.id.tvResponsiblePerson)
        responsiblePersonList = findViewById(R.id.spResponsiblePerson)

        dueDate = findViewById(R.id.tvDueDate)
        dateButton = findViewById(R.id.btCalender)

        reportingTo = findViewById(R.id.tvReportingTo)
        reportingToList = findViewById(R.id.spReportingTo)

        linearLayout = findViewById(R.id.linearLayout)

        correctiveAction=findViewById(R.id.btnCorrectiveAction)
        comment=findViewById(R.id.btnComments)
        docUpload=findViewById(R.id.btnInspectionCompleted)

        correctiveAction.setOnClickListener {
            val intent= Intent(this,CorrectiveAction::class.java)
            startActivity(intent)
        }
        comment.setOnClickListener {
            val intent=Intent(this, Comment::class.java)
            startActivity(intent)
        }
        docUpload.setOnClickListener {

        }

        dateButton.setOnClickListener {
            showDateDialog()
        }

        lifecycleScope.launch {

            val id = intent.getStringExtra("id")

            val showInspectionResponse = try {
                ShowInspectionDetailsInstance.api.getShowInspectionDetails(id!!)
            } catch (e: HttpException) {
                Toast.makeText(this@ShowInspectionDetailsActivity, e.message, Toast.LENGTH_SHORT)
                    .show()
                return@launch
            } catch (e: IOException) {
                Toast.makeText(this@ShowInspectionDetailsActivity, e.message, Toast.LENGTH_SHORT)
                    .show()
                return@launch
            }
            if (showInspectionResponse.isSuccessful && showInspectionResponse.body() != null) {
                category.text =
                    showInspectionResponse.body()!!.content.inspectionCategoryMaster.name
                inspectionType.text = showInspectionResponse.body()!!.content.inspectionType.name
                inspectionType.text = showInspectionResponse.body()!!.content.inspectionType.name
                inspectionType.text = showInspectionResponse.body()!!.content.inspectionType.name
                inspectionType.text = showInspectionResponse.body()!!.content.inspectionType.name
                inspectionType.text = showInspectionResponse.body()!!.content.inspectionType.name

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.edit_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.edit) {
            if (item.title == "EDIT") {
                item.title = "SAVE"
                editViewChanges()

            } else {
                item.title = "EDIT"
                saveViewChanges()
            }


        }
        return super.onOptionsItemSelected(item)
    }


    private fun showDateDialog() {
        val constraintsBuilder = CalendarConstraints.Builder()
        val currentDate = MaterialDatePicker.todayInUtcMilliseconds()

        constraintsBuilder.setStart(currentDate)


        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select Date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()



        datePicker.addOnPositiveButtonClickListener { selection ->
            val selectedDate = selection

        }
        datePicker.addOnNegativeButtonClickListener {

        }

        datePicker.addOnCancelListener {

        }
        datePicker.addOnDismissListener {

        }

        datePicker.show(supportFragmentManager, "")
    }

    private fun editViewChanges() {
        linearLayout.visibility = View.GONE

        categoryList.visibility = View.VISIBLE
        category.visibility = View.GONE

        inspectionType.visibility = View.GONE
        inspectionTypeList.visibility = View.VISIBLE

        site.visibility = View.GONE
        siteText.visibility = View.VISIBLE

        inspectionLocation.visibility = View.GONE
        inspectionLocationList.visibility = View.VISIBLE

        responsiblePerson.visibility = View.GONE
        responsiblePersonList.visibility = View.VISIBLE


        dateButton.visibility = View.VISIBLE

        reportingTo.visibility = View.GONE
        reportingToList.visibility = View.VISIBLE
        dateButton.visibility = View.VISIBLE
    }

    private fun saveViewChanges() {
        linearLayout.visibility = View.VISIBLE

        categoryList.visibility = View.GONE
        category.visibility = View.VISIBLE

        inspectionType.visibility = View.VISIBLE
        inspectionTypeList.visibility = View.GONE

        site.visibility = View.VISIBLE
        siteText.visibility = View.GONE

        inspectionLocation.visibility = View.VISIBLE
        inspectionLocationList.visibility = View.GONE

        responsiblePerson.visibility = View.VISIBLE
        responsiblePersonList.visibility = View.GONE

        dateButton.visibility = View.GONE

        reportingTo.visibility = View.VISIBLE

        dateButton.visibility = View.GONE

    }
}