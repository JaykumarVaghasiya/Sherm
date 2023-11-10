package com.jay.shermassignment.di.viewmodels.Inspection

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jay.shermassignment.di.application.ShermApp
import com.jay.shermassignment.dataModel.showInspection.ShowInspectionDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InspectionDetailViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    val _inspectionLiveData=MutableLiveData<ShowInspectionDetails>()
    val _errorLiveData=MutableLiveData<String?>()

    fun inspectionDetail(id:Int){
        viewModelScope.launch(Dispatchers.IO) {
            val inspectionDetailsResponse = ShermApp.inspectionDetailRepository.getAllInspectionDetail(id)

            if (inspectionDetailsResponse is ShowInspectionDetails) {
                _inspectionLiveData.postValue(inspectionDetailsResponse!!)
            } else if (inspectionDetailsResponse is String) {
                _errorLiveData.postValue(inspectionDetailsResponse)
            }else{
                Log.e("error", "?????????????")
            }
        }
    }
}