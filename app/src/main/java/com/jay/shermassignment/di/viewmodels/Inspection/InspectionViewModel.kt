package com.jay.shermassignment.di.viewmodels.Inspection

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.jay.shermassignment.di.repository.inspection.InspectionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InspectionViewModel @Inject constructor(
    inspectionRepository: InspectionRepository,
    application: Application
) : AndroidViewModel(application) {

    val list = inspectionRepository.getInspectionList().cachedIn(viewModelScope)

}