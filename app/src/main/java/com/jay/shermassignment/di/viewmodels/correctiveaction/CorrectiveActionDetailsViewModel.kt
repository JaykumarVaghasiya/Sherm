package com.jay.shermassignment.di.viewmodels.correctiveaction

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jay.shermassignment.dataModel.correctiveactionalldetails.CorrectiveActionDetailsResponse
import com.jay.shermassignment.dataModel.duedate.GetDueDateResponse
import com.jay.shermassignment.di.application.ShermApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CorrectiveActionDetailsViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {
    val _correctiveActionDetailsLiveData = MutableLiveData<CorrectiveActionDetailsResponse?>()
    val _dueDateLiveData = MutableLiveData<GetDueDateResponse?>()

    val _errorMessageLiveData = MutableLiveData<String?>()

    fun addCorrectiveAction(id: Int) {
        viewModelScope.launch {
            val cAView = ShermApp.correctiveActionDetailsRepository.getCorrectiveActionDetails(id)

            if (cAView is CorrectiveActionDetailsResponse) {
                _correctiveActionDetailsLiveData.postValue(cAView)
            } else if (cAView is String) {
                _errorMessageLiveData.postValue(cAView)
            } else {
                Log.d("response_D", "Invalid")
            }
        }
    }

    fun getDueDate(id:Int){
        viewModelScope.launch {
            val cAView = ShermApp.correctiveActionDetailsRepository.getDueDateDetails(id)

            if (cAView is GetDueDateResponse) {
                _dueDateLiveData.postValue(cAView)
            } else if (cAView is String) {
                _errorMessageLiveData.postValue(cAView)
            } else {
                Log.d("response_D", "Invalid")
            }
        }
    }
}