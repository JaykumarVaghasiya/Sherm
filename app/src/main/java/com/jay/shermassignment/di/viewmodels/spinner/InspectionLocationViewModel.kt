package com.jay.shermassignment.di.viewmodels.spinner

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jay.shermassignment.dataModel.location.InspectionLocationResponse
import com.jay.shermassignment.di.application.ShermApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InspectionLocationViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {


    val _InspectionLocationLiveData = MutableLiveData<InspectionLocationResponse?>()
    val _errorMessageLiveData = MutableLiveData<String?>()

    fun inspectionLocation(categoryId: Int, siteId: Int, inspectionTypeId: Int) {
        viewModelScope.launch {
            val inspectionLocation = ShermApp.inspectionLocationRepository.getInspectionLocation(
                categoryId,
                siteId,
                inspectionTypeId
            )

            if (inspectionLocation is InspectionLocationResponse) {
                _InspectionLocationLiveData.postValue(inspectionLocation)
            } else if (inspectionLocation is String) {
                _errorMessageLiveData.postValue(inspectionLocation)
            } else {
                // Do nothing
            }
        }
    }
}