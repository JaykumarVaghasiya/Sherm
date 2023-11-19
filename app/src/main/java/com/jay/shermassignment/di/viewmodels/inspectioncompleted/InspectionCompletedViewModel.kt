package com.jay.shermassignment.di.viewmodels.inspectioncompleted

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jay.shermassignment.dataModel.addinspectioncompletted.CompletedInspectionBody
import com.jay.shermassignment.dataModel.addinspectioncompletted.CompletedResponse
import com.jay.shermassignment.di.application.ShermApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class InspectionCompletedViewModel @Inject constructor(application: Application):AndroidViewModel(application) {
    val _inspectiomCompletedLiveData = MutableLiveData<CompletedResponse?>()
    val _errorMessageLiveData = MutableLiveData<String?>()

    fun dueDateApprove(completedInspectionBody: CompletedInspectionBody) {
        viewModelScope.launch {
            val inspectionCompleted =
                ShermApp.inspectionCompletedRepository.isInspectionCompleted(completedInspectionBody)

            if(inspectionCompleted is CompletedResponse){
                _inspectiomCompletedLiveData.postValue(inspectionCompleted)
            }else if(inspectionCompleted is String){
                _errorMessageLiveData.postValue(inspectionCompleted)
            }else{
                Log.d("response_D","Invalid")
            }
        }
    }
}