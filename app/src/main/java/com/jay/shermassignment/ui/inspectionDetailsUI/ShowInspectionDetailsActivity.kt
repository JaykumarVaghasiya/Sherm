package com.jay.shermassignment.ui.inspectionDetailsUI

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.jay.shermassignment.R
import com.jay.shermassignment.ui.addinpectioncompleted.AddInspectionCompleted
import com.jay.shermassignment.ui.commentUI.Comment
import com.jay.shermassignment.ui.correctiveaction.CorrectiveAction
import com.jay.shermassignment.utils.SessionManager
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class ShowInspectionDetailsActivity : AppCompatActivity() {

    private lateinit var correctiveAction: MaterialButton
    private lateinit var comment: MaterialButton
    private lateinit var docUpload: MaterialButton

    private lateinit var category: MaterialTextView

    private lateinit var inspectionType: MaterialTextView

    private lateinit var site: MaterialTextView

    private lateinit var inspectionLocation: MaterialTextView

    private lateinit var responsiblePerson: MaterialTextView

    private lateinit var dueDate: MaterialTextView

    private lateinit var reportingTo: MaterialTextView

    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_inspection_details2)

        val id = intent.getIntExtra("id", 0)
        progressBar = findViewById(R.id.progressBar3)
        progressBar.bringToFront()
        category = findViewById(R.id.tvCategory)
        inspectionType = findViewById(R.id.tvInspectionType)
        site = findViewById(R.id.tvSite)
        inspectionLocation = findViewById(R.id.tvInspectionLocation)
        responsiblePerson = findViewById(R.id.tvResponsiblePerson)
        dueDate = findViewById(R.id.tvDueDate)
        reportingTo = findViewById(R.id.tvReportingTo)
        correctiveAction = findViewById(R.id.btnCorrectiveAction)
        comment = findViewById(R.id.btnComments)
        docUpload = findViewById(R.id.btnInspectionCompleted)

        correctiveAction.setOnClickListener {
            val intent = Intent(this, CorrectiveAction::class.java)
            startActivity(intent)
            overridePendingTransition(
                com.google.android.material.R.anim.abc_grow_fade_in_from_bottom,
                R.anim.slide_out_to_left
            )
        }
        comment.setOnClickListener {
            val intent = Intent(this, Comment::class.java)
            startActivity(intent)
            overridePendingTransition(
                com.google.android.material.R.anim.abc_grow_fade_in_from_bottom,
                R.anim.slide_out_to_left
            )
        }
        docUpload.setOnClickListener {
            showConfirmationDialog(this)
        }

        val authToken = SessionManager(this).fetchAuthToken()
        progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            val showInspectionResponse = try {
                ShowInspectionDetailsInstance.api.getShowInspectionDetails(id, "Bearer $authToken")
            } catch (e: HttpException) {
                Toast.makeText(this@ShowInspectionDetailsActivity, e.message, Toast.LENGTH_SHORT)
                    .show()
                return@launch
            } catch (e: IOException) {
                Toast.makeText(this@ShowInspectionDetailsActivity, e.message, Toast.LENGTH_SHORT)
                    .show()
                return@launch
            } finally {
                progressBar.visibility = View.GONE
            }
            if (showInspectionResponse.isSuccessful && showInspectionResponse.body() != null) {

                val data = showInspectionResponse.body()!!.content
                category.text =
                    data.inspectionCategoryMaster.name

                inspectionType.text = data.inspectionType.name
                inspectionLocation.text = data.inspectionLocation

                responsiblePerson.text =
                    data.responsiblePerson.user.username

                reportingTo.text = data.asignee.toString()
                dueDate.text = data.dueDate
                site.text = data.workplaceInspection.site.name
            } else {
                Toast.makeText(this@ShowInspectionDetailsActivity, "EMPTY Bro", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("category", category.text.toString())
        outState.putString("inspectionType", inspectionType.text.toString())
        outState.putString("site", site.text.toString())
        outState.putString("inspectionLocation", inspectionLocation.text.toString())
        outState.putString("responsiblePerson", responsiblePerson.text.toString())
        outState.putString("dueDate", dueDate.text.toString())
        outState.putString("reportingTo", reportingTo.text.toString())
    }

    private fun showConfirmationDialog(context: Context) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Confirm")
        alertDialog.setMessage("Documents are uploaded?")
        alertDialog.setPositiveButton("OK") { dialog, _ ->
            val intent = Intent(context, AddInspectionCompleted::class.java)
            startActivity(intent)
            overridePendingTransition(
                com.google.android.material.R.anim.abc_grow_fade_in_from_bottom,
                R.anim.slide_out_to_left
            )
            dialog.dismiss()
        }
        alertDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.show()
    }

}