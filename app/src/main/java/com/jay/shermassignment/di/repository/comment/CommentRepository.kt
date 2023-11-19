package com.jay.shermassignment.di.repository.comment

import android.content.Context
import android.util.Log
import com.jay.shermassignment.dataModel.comments.CommentBody
import com.jay.shermassignment.ui.commentUI.CommentInstance
import com.jay.shermassignment.utils.SessionManager
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommentRepository @Inject constructor(@ApplicationContext context: Context) {

    val authToken=SessionManager(context).fetchAuthToken()

    suspend fun addCommentToInspection(commentBody: CommentBody):Any?{

        val commentResponse=try {
            CommentInstance.api.addCommentsForInspection(commentBody,"Bearer $authToken")
        }catch (e:IOException){
        return e.message
        }catch (e:HttpException){
            return  e.message
        }catch (e:Exception){
            return e.message
        }
        return if(commentResponse.isSuccessful && commentResponse.body() != null){
            commentResponse.body() ?: ""
        }else{
            Log.e("response","EMPTY")
        }
    }
}