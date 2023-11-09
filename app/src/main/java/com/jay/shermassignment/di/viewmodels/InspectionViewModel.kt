package com.jay.shermassignment.di.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.jay.shermassignment.di.repository.inspection.InspectionRepository
import com.jay.shermassignment.model.inspection.DeleteInspection
import com.jay.shermassignment.model.inspection.Row
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InspectionViewModel @Inject constructor(
    val inspectionRepository: InspectionRepository,
    application: Application
) : AndroidViewModel(application) {

    val list = inspectionRepository.getInspectionList().cachedIn(viewModelScope)
    val _inspectionLiveData= MutableLiveData<DeleteInspection?>()
    val _errorLiveData= MutableLiveData<String?>()

    fun deleteInspection(row: Row, id: Int) {
        viewModelScope.launch {
            val deleteInsp = inspectionRepository.deleteInspection(row, id)

            if(deleteInsp is DeleteInspection ){
                _inspectionLiveData.postValue(deleteInsp)
                return@launch
            }else if(deleteInsp is String?){
                _errorLiveData.postValue(deleteInsp)
            }else{
                //Do Nothing
            }
        }
    }
}