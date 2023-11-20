package com.jay.shermassignment.ui.corrective_evaluation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.jay.shermassignment.R
import com.jay.shermassignment.databinding.ActivityCorrecitveEvalutionBinding
import com.jay.shermassignment.di.viewmodels.correctiveevaluation.CorrectiveEvaluationViewModel
import com.jay.shermassignment.generic.setupSpinnerFromArray
import com.jay.shermassignment.generic.showConfirmationDialog

class CorrectiveEvaluation : AppCompatActivity() {
private lateinit var binding:ActivityCorrecitveEvalutionBinding
private lateinit var correctiveEvaluationViewModel:CorrectiveEvaluationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCorrecitveEvalutionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setTitle(R.string.corrective_evaluation)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        correctiveEvaluationViewModel=ViewModelProvider(this)[CorrectiveEvaluationViewModel::class.java]
        spinnerValues()
    }

    private fun spinnerValues() {
        setupSpinnerFromArray(binding.spStatusCorrectiveEvaluation, R.array.statusCE)
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
            "Assigned" -> 1
            else -> 2
        }
    }

    private fun saveCorrectiveEvaluation() {

        val id = intent.getIntExtra("correctiveActionId", 0)
        val comment = binding.etCorrectiveEvaluationComments.text.toString()
        val statusValue = binding.spStatusCorrectiveEvaluation.selectedItem.toString()
        val statusIntValue = getStatusValue(statusValue)

        val body = CorrectiveEvaluationBody(statusIntValue, comment, id)

        correctiveEvaluationViewModel.getCorrectiveEvaluation(body)
        correctiveEvaluationViewModel._correctiveEvaluationLiveData.observe(this){cEResponse->
            if(cEResponse?.isSuccess ==true){
                showConfirmationDialog(getString(R.string.sucess),getString(R.string.saved_sucessfully)){
                    finish()
                }
            }
        }

        correctiveEvaluationViewModel._errorMessageLiveData.observe(this){error->
            if(error != null){
                showConfirmationDialog(getString(R.string.error),error)
            }
        }
    }
}