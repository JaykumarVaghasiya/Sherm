package com.jay.shermassignment.di.viewmodels.correctiveaction

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jay.shermassignment.dataModel.addcorrectiveaction.AddCorrectiveActionResponse
import com.jay.shermassignment.dataModel.correctiveaction.CorrectiveActionResponseX
import com.jay.shermassignment.di.application.ShermApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CorrectiveActionViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {
    val _correctiveActionLiveData = MutableLiveData<CorrectiveActionResponseX?>()
    val _errorMessageLiveData = MutableLiveData<String?>()

    fun correctiveAction(id: Int) {
        viewModelScope.launch {
            val correctiveAction =
                ShermApp.correctiveActionRepository.getAllCorrectiveActionList(id)

            if (correctiveAction is CorrectiveActionResponseX) {
                _correctiveActionLiveData.postValue(correctiveAction)
            } else if (correctiveAction is String) {
                _errorMessageLiveData.postValue(correctiveAction)
            } else {
                Log.d("response_D", "Invalid")
            }
        }
    }
}