package com.jay.shermassignment.di.viewmodels.correctiveaction

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jay.shermassignment.dataModel.addcorrectiveaction.AddCorrectiveActionResponse
import com.jay.shermassignment.dataModel.addinspection.AddInspectionResponse
import com.jay.shermassignment.di.application.ShermApp
import com.jay.shermassignment.ui.add_corrective_action.AddCorrectiveActionBody
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCorrectiveActionViewModel @Inject constructor(application: Application):AndroidViewModel(application){
    val _addCorrectiveActionLiveData = MutableLiveData<AddCorrectiveActionResponse?>()
    val _errorMessageLiveData = MutableLiveData<String?>()

    fun addCorrectiveAction(addCorrectiveActionBody: AddCorrectiveActionBody){
        viewModelScope.launch {
            val addCA=ShermApp.addCorrectiveActionRepository.addCorrectiveAction(addCorrectiveActionBody)

            if(addCA is AddCorrectiveActionResponse){
                _addCorrectiveActionLiveData.postValue(addCA)
            }else if(addCA is String){
                _errorMessageLiveData.postValue(addCA)
            }else{
                Log.d("response_D","Invalid")
            }
        }
    }
}