package com.jay.shermassignment.di.viewmodels.Inspection

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jay.shermassignment.di.application.ShermApp
import com.jay.shermassignment.dataModel.addInspectionData.AddInspectionRef
import com.jay.shermassignment.dataModel.addinspection.AddInspectionResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddInspectionViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    val _addInspectionLiveData = MutableLiveData<AddInspectionResponse?>()
    val _errorMessageLiveData = MutableLiveData<String?>()

    fun addInspection(addInspectionRef: AddInspectionRef) {
        viewModelScope.launch {
            val addInspectionResponse =
                ShermApp.addInspectionRepository.addInspectionToList(addInspectionRef)

            if(addInspectionResponse is AddInspectionResponse){
                _addInspectionLiveData.postValue(addInspectionResponse)
            }else if(addInspectionResponse is String){
                _errorMessageLiveData.postValue(addInspectionResponse)
            }else{
                Log.d("response_D","Invalid")
            }
        }
    }
}