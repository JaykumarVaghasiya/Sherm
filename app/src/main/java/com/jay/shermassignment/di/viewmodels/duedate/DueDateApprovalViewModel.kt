package com.jay.shermassignment.di.viewmodels.duedate

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jay.shermassignment.dataModel.approveca.ApproveCABody
import com.jay.shermassignment.dataModel.approveca.ApproveCAResponse
import com.jay.shermassignment.di.application.ShermApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DueDateApprovalViewModel @Inject constructor(application: Application):AndroidViewModel(application) {

    val _dueDateLiveData = MutableLiveData<ApproveCAResponse?>()
    val _errorMessageLiveData = MutableLiveData<String?>()

    fun dueDateApprove(approveCABody: ApproveCABody) {
        viewModelScope.launch {
            val dueDateApproval =
                ShermApp.dueDateExtendedRepository.approveDueDate(approveCABody)

            if(dueDateApproval is ApproveCAResponse){
                _dueDateLiveData.postValue(dueDateApproval)
            }else if(dueDateApproval is String){
                _errorMessageLiveData.postValue(dueDateApproval)
            }else{
                Log.d("response_D","Invalid")
            }
        }
    }

    fun dueDateReject(rejectCABody: ApproveCABody) {
        viewModelScope.launch {
            val dueDateReject =
                ShermApp.dueDateExtendedRepository.rejectDueDate(rejectCABody)

            if(dueDateReject is ApproveCAResponse){
                _dueDateLiveData.postValue(dueDateReject)
            }else if(dueDateReject is String){
                _errorMessageLiveData.postValue(dueDateReject)
            }else{
                Log.d("response_D","Invalid")
            }
        }
    }
}