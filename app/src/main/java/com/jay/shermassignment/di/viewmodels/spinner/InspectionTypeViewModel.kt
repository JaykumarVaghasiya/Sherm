package com.jay.shermassignment.di.viewmodels.spinner

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jay.shermassignment.di.application.ShermApp
import com.jay.shermassignment.dataModel.inspectiontype.InspectionTypeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InspectionTypeViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    val _InspectionTypeLiveData = MutableLiveData<InspectionTypeResponse?>()
    val _errorMessageLiveData = MutableLiveData<String?>()

    fun inspectionType(id: Int) {
        viewModelScope.launch {
            val inspectionType = ShermApp.inspectionTypeRepository.getInspectionType(id)

            if (inspectionType is InspectionTypeResponse) {
                _InspectionTypeLiveData.postValue(inspectionType)
            } else if (inspectionType is String) {
                _errorMessageLiveData.postValue(inspectionType)
            } else {
                //Do Nothing
            }
        }
    }
}