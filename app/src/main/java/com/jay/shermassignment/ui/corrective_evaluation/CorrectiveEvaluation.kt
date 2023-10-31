package com.jay.shermassignment.ui.corrective_evaluation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jay.shermassignment.R
import com.jay.shermassignment.generic.setupSpinnerFromArray
import com.jay.shermassignment.generic.showConfirmationDialog
import com.jay.shermassignment.utils.SessionManager
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class CorrectiveEvaluation : AppCompatActivity() {

    private lateinit var comment: EditText
    private lateinit var status: Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_correcitve_evalution)
        supportActionBar?.setTitle(R.string.corrective_evaluation)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initializeView()
        spinnerValues()
    }

    private fun spinnerValues() {
        setupSpinnerFromArray(status, R.array.statusCE)
    }

    private fun initializeView() {
        comment = findViewById(R.id.etCorrectiveEvaluationComments)
        status = findViewById(R.id.spStatusCorrectiveEvaluation)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> {
                saveCorrectiveEvaluation()
            }
            android.R.id.home ->{
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getStatusValue(statusValue: String): Int {
        return when (statusValue) {
            "Assigned" -> 2
            else -> 3
        }
    }

    private fun saveCorrectiveEvaluation() {
        val authToken = SessionManager(this).fetchAuthToken()
        val id = intent.getIntExtra("correctiveActionId", 0)
        val comment = comment.text.toString()
        val statusValue = status.selectedItem.toString()
        val statusIntValue = getStatusValue(statusValue)

        val body = CorrectiveEvaluationBody(statusIntValue, comment, id)

        lifecycleScope.launch {
            val correctiveEvaluation = try {
                CorrectiveEvaluationInstance.api.addEvaluation(body, authToken!!)
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
            if(correctiveEvaluation.isSuccessful && correctiveEvaluation.body() != null){
                showConfirmationDialog(getString(R.string.sherm),getString(R.string.added))
                finish()
            }
        }
    }


}