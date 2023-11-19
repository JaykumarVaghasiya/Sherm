package com.jay.shermassignment.di.repository.duedate

import android.content.Context
import android.util.Log
import com.jay.shermassignment.ui.corrective_action_details.CAViewInstance
import com.jay.shermassignment.utils.SessionManager
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CheckDueDateRepository @Inject constructor(@ApplicationContext context: Context) {

 val authToken=SessionManager(context).fetchAuthToken()

    suspend fun checkDueDate(id:Int):Any?{
        val dueDate=try {
            CAViewInstance.api.checkDueDateExtend(id,"Bearer $authToken")
        }catch (e: Exception) {
            return e.message
        } catch (e: IOException) {
            return e.message
        } catch (e: HttpException) {
            return e.message
        }
        return if(dueDate.isSuccessful && dueDate.body() != null){
            dueDate.body() ?: ""
        }else{
            Log.e("response","Empty")
        }
    }
}