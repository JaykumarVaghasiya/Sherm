package com.jay.shermassignment.ui.inspectionDetailsUI

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.jay.shermassignment.R
import com.jay.shermassignment.generic.showToast
import com.jay.shermassignment.generic.startActivityStart
import com.jay.shermassignment.ui.add_inspection_completed.AddInspectionCompleted
import com.jay.shermassignment.ui.commentUI.Comment
import com.jay.shermassignment.ui.corrective_action.CorrectiveAction
import com.jay.shermassignment.utils.SessionManager
import kotlinx.coroutines.launch


class ShowInspectionDetailsActivity : AppCompatActivity() {
    private lateinit var correctiveAction: MaterialButton
    private lateinit var comment: MaterialButton
    private lateinit var docUpload: MaterialButton
    private lateinit var reschedule: MaterialTextView
    private lateinit var category: MaterialTextView
    private lateinit var inspectionType: MaterialTextView
    private lateinit var site: MaterialTextView
    private lateinit var inspectionLocation: MaterialTextView
    private lateinit var responsiblePerson: MaterialTextView
    private lateinit var dueDate: MaterialTextView
    private lateinit var progressBar: ProgressBar
    private var inspectionId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_inspection_details2)
        val id =intent.getStringExtra("inspectionId")
        inspectionId=intent.getIntExtra("id",0)
        supportActionBar?.title = id
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initializeViews()
        setupClickListeners()
        retrieveInstanceState(savedInstanceState)
        fetchData()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
              finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun fetchData() {
        val authToken = SessionManager(this).fetchAuthToken()
        progressBar.visibility = View.VISIBLE


        lifecycleScope.launch {
            val showInspectionResponse = try {
                ShowInspectionDetailsInstance.api.getShowInspectionDetails(
                    inspectionId,
                    "Bearer $authToken"
                )
            } catch (e: Exception) {
                showToast(e.message)
                return@launch
            } finally {
                progressBar.visibility = View.GONE
            }

            if (showInspectionResponse.isSuccessful && showInspectionResponse.body() != null) {
                val data = showInspectionResponse.body()!!.content

                category.text = data.inspectionCategoryMaster.name
                inspectionType.text = data.inspectionType.name
                inspectionLocation.text = data.inspectionLocation
                responsiblePerson.text = data.responsiblePerson.user.username
                dueDate.text = data.dueDate
                site.text = data.workplaceInspection.site.name
                reschedule.text = data.reschedule.toString()
            } else {
                showToast(getString(R.string.nothing_to_see_here))
            }
        }
    }

    private fun initializeViews() {
        progressBar = findViewById(R.id.progressBar3)
        category = findViewById(R.id.tvCategory)
        inspectionType = findViewById(R.id.tvInspectionType)
        site = findViewById(R.id.tvSite)
        inspectionLocation = findViewById(R.id.tvInspectionLocation)
        responsiblePerson = findViewById(R.id.tvResponsiblePerson)
        dueDate = findViewById(R.id.tvDueDate)
        correctiveAction = findViewById(R.id.btnCorrectiveAction)
        comment = findViewById(R.id.btnComments)
        docUpload = findViewById(R.id.btnInspectionCompleted)
        reschedule = findViewById(R.id.tvReportingTo)
    }

    private fun setupClickListeners() {

        correctiveAction.setOnClickListener {
            val intent = Intent(this, CorrectiveAction::class.java)
            intent.putExtra("ids", inspectionId)
            startActivity(intent)
        }
        comment.setOnClickListener {
            val intent = Intent(this, Comment::class.java)
            intent.putExtra("ids", inspectionId)
            startActivity(intent)
        }
        docUpload.setOnClickListener {
            showConfirmationDialog()
           }
    }

    private fun retrieveInstanceState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            category.text = savedInstanceState.getString("category", "")
            inspectionType.text = savedInstanceState.getString("inspectionType", "")
            site.text = savedInstanceState.getString("site", "")
            inspectionLocation.text = savedInstanceState.getString("inspectionLocation", "")
            responsiblePerson.text = savedInstanceState.getString("responsiblePerson", "")
            dueDate.text = savedInstanceState.getString("dueDate", "")
            reschedule.text = savedInstanceState.getString("reportingTo", "")
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("category", category.text.toString())
        outState.putString("inspectionType", inspectionType.text.toString())
        outState.putString("site", site.text.toString())
        outState.putString("inspectionLocation", inspectionLocation.text.toString())
        outState.putString("responsiblePerson", responsiblePerson.text.toString())
        outState.putString("dueDate", dueDate.text.toString())
        outState.putString("reportingTo", reschedule.text.toString())
    }
    private fun showConfirmationDialog() {

        val dialog = AlertDialog.Builder(this)
        dialog.setTitle(getString(R.string.confirm))
        dialog.setMessage(getString(R.string.document_are_uploaded))
        dialog.setPositiveButton("OK") { d, _ ->
            val id = intent.getIntExtra("id", 0)
            intent.putExtra("ids", id)
            startActivityStart<AddInspectionCompleted>()
            d.dismiss()
        }
        dialog.setNegativeButton("Cancel") { d, _ ->
            d.dismiss()
        }
        dialog.show()
    }

}
