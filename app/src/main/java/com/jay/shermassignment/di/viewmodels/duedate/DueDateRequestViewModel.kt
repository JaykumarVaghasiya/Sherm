package com.jay.shermassignment.di.viewmodels.duedate

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jay.shermassignment.dataModel.addInspectionData.AddInspectionRef
import com.jay.shermassignment.dataModel.addinspection.AddInspectionResponse
import com.jay.shermassignment.dataModel.extenddate.ExtendDateBody
import com.jay.shermassignment.dataModel.extenddate.ExtendDateResponse
import com.jay.shermassignment.di.application.ShermApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class DueDateRequestViewModel @Inject constructor(application: Application):AndroidViewModel(application) {
    val _dueDateRequestLiveData = MutableLiveData<ExtendDateResponse?>()
    val _errorMessageLiveData = MutableLiveData<String?>()

    fun addInspection(extendDateBody: ExtendDateBody) {
        viewModelScope.launch {
            val dueDateResponse =
                ShermApp.dueDateRequestRepository.dueDateRequest(extendDateBody)

            if(dueDateResponse is ExtendDateResponse){
                _dueDateRequestLiveData.postValue(dueDateResponse)
            }else if(dueDateResponse is String){
                _errorMessageLiveData.postValue(dueDateResponse)
            }else{
                Log.d("response_D","Invalid")
            }
        }
    }
}