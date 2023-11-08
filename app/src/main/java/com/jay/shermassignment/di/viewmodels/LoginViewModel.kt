package com.jay.shermassignment.di.viewmodels
// Create a ViewModel class, e.g., LoginViewModel.kt


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jay.shermassignment.di.application.ShermApp
import com.jay.shermassignment.model.user.UserResponse
import com.jay.shermassignment.ui.login.UserDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    application: Application
) :
    AndroidViewModel(application) {

    val _userResponseLiveData = MutableLiveData<UserResponse?>()
    val _errorMessageLiveData = MutableLiveData<String?>()

    fun login(userDetails: UserDetails) {
        viewModelScope.launch(Dispatchers.IO) {
            val userResponse = ShermApp.loginRepository.getUserDetails(userDetails)

            if (userResponse is UserResponse) {
                _userResponseLiveData.postValue(userResponse)
            } else if (userResponse is String) {
                _errorMessageLiveData.postValue(userResponse)
            }else{
                //Do nothing
            }
        }
    }
}
