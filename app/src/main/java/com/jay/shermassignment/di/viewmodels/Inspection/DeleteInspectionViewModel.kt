package com.jay.shermassignment.di.viewmodels.Inspection

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jay.shermassignment.dataModel.inspection.DeleteInspection
import com.jay.shermassignment.di.repository.inspection.InspectionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteInspectionViewModel @Inject constructor(
    val inspectionRepository: InspectionRepository,
    application: Application
) : AndroidViewModel(application) {

    val deleteInspectionLiveData = MutableLiveData<DeleteInspection?>()
    val _errorLiveData = MutableLiveData<String?>()

    fun deleteInspection(id: Int) {
        viewModelScope.launch {
            val deleteInsp = inspectionRepository.deleteInspection(id)

            if (deleteInsp is DeleteInspection) {
                deleteInspectionLiveData.postValue(deleteInsp)
            } else if (deleteInsp is String?) {
                _errorLiveData.postValue(deleteInsp)
            } else {
                //Do Nothing
            }
        }
    }
}