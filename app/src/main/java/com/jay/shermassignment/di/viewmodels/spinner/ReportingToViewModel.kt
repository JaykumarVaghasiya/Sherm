package com.jay.shermassignment.di.viewmodels.spinner

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jay.shermassignment.di.application.ShermApp
import com.jay.shermassignment.dataModel.reportingTo.ReportingToResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportingToViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {
    val _reportingToLiveData = MutableLiveData<ReportingToResponse?>()
    val _errorMessageLiveData = MutableLiveData<String?>()

    fun reportingTo() {
        viewModelScope.launch {
            val reportingToResponse = ShermApp.reportingToRepository.getReportingTo()

            if (reportingToResponse is ReportingToResponse) {
                _reportingToLiveData.postValue(reportingToResponse)
            } else if (reportingToResponse is String) {
                _errorMessageLiveData.postValue(reportingToResponse)
            } else {
                //Do nothing
            }
        }
    }
}