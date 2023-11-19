package com.jay.shermassignment.di.viewmodels.comment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jay.shermassignment.dataModel.comments.CommentBody
import com.jay.shermassignment.dataModel.comments.CommentResponse
import com.jay.shermassignment.di.application.ShermApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CommentViewModel @Inject constructor(application: Application):AndroidViewModel(application) {
    val _addCommentLiveData = MutableLiveData<CommentResponse?>()
    val _errorMessageLiveData = MutableLiveData<String?>()

    fun addCorrectiveAction(commentBody: CommentBody){
        viewModelScope.launch {
            val addComment= ShermApp.commentRepository.addCommentToInspection(commentBody)

            if(addComment is CommentResponse){
                _addCommentLiveData.postValue(addComment)
            }else if(addComment is String){
                _errorMessageLiveData.postValue(addComment)
            }else{
                Log.d("response_D","Invalid")
            }
        }
    }
}