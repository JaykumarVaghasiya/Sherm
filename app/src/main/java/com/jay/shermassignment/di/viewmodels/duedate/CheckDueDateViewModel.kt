package com.jay.shermassignment.di.viewmodels.duedate

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jay.shermassignment.dataModel.approveca.ApproveCABody
import com.jay.shermassignment.dataModel.duedate.GetDueDateResponse
import com.jay.shermassignment.di.application.ShermApp
import com.jay.shermassignment.utils.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CheckDueDateViewModel @Inject constructor(application: Application):AndroidViewModel(application) {

    val _checkDueDateLiveData = MutableLiveData<GetDueDateResponse?>()
    val _errorMessageLiveData = MutableLiveData<String?>()

    fun dueDateApprove(id:Int) {
        viewModelScope.launch {
            val dueDate =
                ShermApp.checkDueDateRepository.checkDueDate(id)

            if(dueDate is GetDueDateResponse){
                _checkDueDateLiveData.postValue(dueDate)
            }else if(dueDate is String){
                _errorMessageLiveData.postValue(dueDate)
            }else{
                Log.d("response_D","Invalid")
            }
        }
    }

}