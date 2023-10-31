package com.jay.shermassignment.ui.dueDate

import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.jay.shermassignment.R
import com.jay.shermassignment.generic.showConfirmationDialog
import com.jay.shermassignment.generic.showGenericDateDialog
import com.jay.shermassignment.response.duedateapproval.DueDateApprovalBody
import com.jay.shermassignment.response.duedateapproval.DueDateExtension
import com.jay.shermassignment.ui.corrective_action_details.CAViewInstance
import com.jay.shermassignment.utils.SessionManager
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DueDateExtendedApproval : AppCompatActivity() {
    private lateinit var dateText: MaterialTextView
    private lateinit var date: LinearLayout
    private lateinit var comment: EditText
    private lateinit var rejectBt: MaterialButton
    private lateinit var approveBt: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_due_date_extended_approval)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setTitle(R.string.due_date_extended_apporval)
        initializeView()
        getDate()
        btClickListener()
    }

    private fun initializeView() {
        dateText = findViewById(R.id.tvPreferredDateApproval)
        comment = findViewById(R.id.etmComment)
        date = findViewById(R.id.llCalender)
        rejectBt = findViewById(R.id.btnReject)
        approveBt = findViewById(R.id.btnApprove)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
           finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun btClickListener() {
        approveBt.setOnClickListener {
            approveData()
        }
        rejectBt.setOnClickListener {
            rejectData()
            finish()
        }
        date.setOnClickListener {
            showGenericDateDialog(
                R.string.selectedDate.toString(),
                System.currentTimeMillis()
            ) { selectedDate ->
                val formattedDate =
                    SimpleDateFormat("dd-MM-yyyy", Locale.US).format(Date(selectedDate))
                dateText.text = formattedDate
            }
        }
    }

    private fun getDate(){
        val id = intent.getIntExtra("cID", 1)
        val authToken = SessionManager(this).fetchAuthToken()

        lifecycleScope.launch {
            val requestResponse = try {
                CAViewInstance.api.getDueDateExtendedRequest(id, "Bearer $authToken")
            } catch (e: Exception) {
                showConfirmationDialog(getString(R.string.error), e.message)
                return@launch
            } catch (e: HttpException) {
                showConfirmationDialog(getString(R.string.error), e.message)
                return@launch
            } catch (e: IOException) {
                showConfirmationDialog(getString(R.string.error), e.message)
                return@launch
            }
            if (requestResponse.isSuccessful && requestResponse.body() != null) {
               val body =requestResponse.body()!!.content

                dateText.text=body.dueDate
            }
        }
    }

    private fun rejectData() {

        val dateTextValue = dateText.text.toString()
        val commentText = comment.text.toString()
        val id = intent.getIntExtra("", 0)
        val status = "rejected"
        val body = DueDateApprovalBody(
            id,
            DueDateExtension(commentText, 0, dateTextValue, commentText, status)
        )
        val authToken = SessionManager(this).fetchAuthToken()

        lifecycleScope.launch {
            val rejectResponse = try {
                DueDateInstance.api.getDueDateApproval(body, id, authToken!!)
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
            if (rejectResponse.isSuccessful && rejectResponse.body() != null) {
                showConfirmationDialog(getString(R.string.sucess),getString(R.string.reject_sucessfully))
            }
        }
    }

    private fun approveData() {
        val dateTextValue = dateText.text.toString()
        val commentText = comment.text.toString()
        val id = intent.getIntExtra("", 0)
        val status = "approved"
        val body = DueDateApprovalBody(
            id,
            DueDateExtension(commentText, 0, dateTextValue, commentText, status)
        )
        val authToken = SessionManager(this).fetchAuthToken()

        lifecycleScope.launch {
            val approveResponse = try {
                DueDateInstance.api.getDueDateApproval(body, id, authToken!!)
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
            if (approveResponse.isSuccessful && approveResponse.body() != null) {
                showConfirmationDialog(getString(R.string.sucess),getString(R.string.approve_sucessfully))
            }
        }
    }
}