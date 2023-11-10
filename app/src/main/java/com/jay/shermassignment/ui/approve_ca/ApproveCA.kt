package com.jay.shermassignment.ui.approve_ca

import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.jay.shermassignment.R
import com.jay.shermassignment.generic.showConfirmationDialog
import com.jay.shermassignment.dataModel.approveca.ApproveCABody
import com.jay.shermassignment.ui.dueDate.DueDateInstance
import com.jay.shermassignment.utils.SessionManager
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ApproveCA : AppCompatActivity() {

    private lateinit var comment:EditText
    private lateinit var reject:MaterialButton
    private lateinit var approve:MaterialButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_approve_ca)
        supportActionBar?.setTitle(getString(R.string.approverejectCA))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initializeView()
        buttonClickListener()
    }

    private fun buttonClickListener() {
        approve.setOnClickListener {
            approveData()
        }
        reject.setOnClickListener {
            rejectData()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun rejectData() {

        val commentText = comment.text.toString()
        val status = "rejected"
        val id=intent.getIntExtra("cID",1)
        val body = ApproveCABody(
            status,commentText,id
        )
        val authToken = SessionManager(this).fetchAuthToken()

        lifecycleScope.launch {
            val rejectResponse = try {
                DueDateInstance.api.getDueDateApproval(body,"Bearer $authToken")
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
                showConfirmationDialog(getString(R.string.sucess),getString(R.string.reject_sucessfully)){
                    finish()
                }
            }
        }
    }

    private fun approveData() {

        val commentText = comment.text.toString()
        val status = "approved"
        val id=intent.getIntExtra("cID",1)
        val body = ApproveCABody(
           status,commentText,id
        )
        val authToken = SessionManager(this).fetchAuthToken()

        lifecycleScope.launch {
            val approveResponse = try {
                DueDateInstance.api.getDueDateApproval(body,"Bearer $authToken")
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
                showConfirmationDialog(getString(R.string.sucess),getString(R.string.approve_sucessfully)){
                    finish()
                }
            }
        }
    }

    private fun initializeView() {
        comment=findViewById(R.id.etmCommentCA)
        approve=findViewById(R.id.btnApproveCA)
        reject=findViewById(R.id.btnRejectCA)
    }
}