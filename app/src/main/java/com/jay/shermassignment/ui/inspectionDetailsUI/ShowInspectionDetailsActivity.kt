package com.jay.shermassignment.ui.inspectionDetailsUI

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.jay.shermassignment.R
import com.jay.shermassignment.databinding.ActivityShowInspectionDetails2Binding
import com.jay.shermassignment.di.viewmodels.Inspection.InspectionDetailViewModel
import com.jay.shermassignment.generic.showConfirmationDialog
import com.jay.shermassignment.ui.add_inspection_completed.AddInspectionCompleted
import com.jay.shermassignment.ui.commentUI.Comment
import com.jay.shermassignment.ui.corrective_action.CorrectiveAction
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class ShowInspectionDetailsActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityShowInspectionDetails2Binding
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                finish()
            }
        }
    private lateinit var viewModel: InspectionDetailViewModel
    private var inspectionId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityShowInspectionDetails2Binding.inflate(layoutInflater)
        setContentView(_binding.root)
        val id = intent.getStringExtra("inspectionId")
        inspectionId = intent.getIntExtra("id", 0)
        viewModel = ViewModelProvider(this)[InspectionDetailViewModel::class.java]
        fetchData()
        supportActionBar?.title = id
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupClickListeners()
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
        viewModel.inspectionDetail(inspectionId)

        viewModel._inspectionLiveData.observe(this) { response ->
            _binding.tvCategory.text = response?.content?.inspectionCategoryMaster?.name
            _binding.tvInspectionLocation.text = response?.content?.inspectionLocation
            _binding.tvInspectionType.text = response?.content?.inspectionType?.name
            _binding.tvSite.text = response?.content?.workplaceInspection?.site?.name
            val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formattedDueDate = dateFormatter.parse(response?.content?.dueDate!!)
            val formattedDueDateString =
                SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(formattedDueDate!!)
            _binding.tvDueDate.text = formattedDueDateString
            _binding.tvReportingTo.text = response.content.reschedule.toString()
            _binding.tvResponsiblePerson.text =
                response.content.responsiblePerson.user.employee.fullName
        }

        viewModel._errorLiveData.observe(this) { errorMessage ->
            if (errorMessage != null) {
                showConfirmationDialog(getString(R.string.error), errorMessage)
            }
        }

    }
    private fun setupClickListeners() {

        _binding.btnCorrectiveAction.setOnClickListener {
            val intent = Intent(this, CorrectiveAction::class.java)
            intent.putExtra("ids", inspectionId)
            startActivity(intent)
        }
        _binding.btnComments.setOnClickListener {
            val intent = Intent(this, Comment::class.java)
            intent.putExtra("ids", inspectionId)
            startActivity(intent)
        }
        _binding.btnInspectionCompleted.setOnClickListener {
            showConfirmationDialog()
        }
    }

    private fun showConfirmationDialog() {

        val dialog = AlertDialog.Builder(this)
        dialog.setTitle(getString(R.string.confirm))
        dialog.setMessage(getString(R.string.document_are_uploaded))
        dialog.setPositiveButton("OK") { d, _ ->
            val id = intent.getIntExtra("id", 0)
            val intent=Intent(this,AddInspectionCompleted::class.java)
            intent.putExtra("ids", id)
            launcher.launch(intent)
            d.dismiss()
        }
        dialog.setNegativeButton("Cancel") { d, _ ->
            d.dismiss()
        }
        dialog.show()
    }

}
