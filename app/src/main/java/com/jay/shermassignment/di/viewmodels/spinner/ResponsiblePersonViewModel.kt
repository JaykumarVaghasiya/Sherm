package com.jay.shermassignment.di.viewmodels.spinner

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jay.shermassignment.di.application.ShermApp
import com.jay.shermassignment.dataModel.responsibleperson.ResponsiblePersonResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResponsiblePersonViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    val _responsiblePersonLiveData = MutableLiveData<ResponsiblePersonResponse?>()
    val _errorMessageLiveData = MutableLiveData<String?>()

    fun responsiblePerson(siteId: Int) {
        viewModelScope.launch {
            val responsiblePerson =
                ShermApp.responsiblePersonRepository.getResponsiblePerson(siteId)
            if (responsiblePerson is ResponsiblePersonResponse) {
                _responsiblePersonLiveData.postValue(responsiblePerson)
            } else if (responsiblePerson is String) {
                _errorMessageLiveData.postValue(responsiblePerson)
            } else {
                // Do nothing
            }

        }
    }
}