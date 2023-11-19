package com.jay.shermassignment.di.viewmodels.correctiveevaluation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jay.shermassignment.dataModel.correctiveevaluation.CorrectiveEvaluationResponse
import com.jay.shermassignment.di.application.ShermApp
import com.jay.shermassignment.ui.corrective_evaluation.CorrectiveEvaluationBody
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CorrectiveEvaluationViewModel @Inject constructor(application: Application):AndroidViewModel(application){
    val _correctiveEvaluationLiveData = MutableLiveData<CorrectiveEvaluationResponse?>()
    val _errorMessageLiveData = MutableLiveData<String?>()

    fun getCorrectiveEvaluation(correctiveEvaluationBody: CorrectiveEvaluationBody){
        viewModelScope.launch {
            val _correctiveEvaluation=
                ShermApp.correctiveEvaluationRepository.correctiveEvaluation(
                    correctiveEvaluationBody
                )

            if(_correctiveEvaluation is CorrectiveEvaluationResponse){
                _correctiveEvaluationLiveData.postValue(_correctiveEvaluation)
            }else if(_correctiveEvaluation is String){
                _errorMessageLiveData.postValue(_correctiveEvaluation)
            }else{
                Log.d("response_D","Invalid")
            }
        }
    }
}