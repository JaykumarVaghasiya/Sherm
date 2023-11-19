package com.jay.shermassignment.di.repository.duedate

import android.content.Context
import android.util.Log
import com.jay.shermassignment.dataModel.extenddate.ExtendDateBody
import com.jay.shermassignment.ui.dueDate.DueDateInstance
import com.jay.shermassignment.utils.SessionManager
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DueDateRequestRepository @Inject constructor(@ApplicationContext context: Context) {
    val authToken = SessionManager(context).fetchAuthToken()

    suspend fun dueDateRequest(extendDateBody: ExtendDateBody): Any? {
        val request = try {
            DueDateInstance.api.getDueDateRequest("request", extendDateBody, "Bearer $authToken")
        } catch (e: Exception) {
            return e.message
        } catch (e: IOException) {
            return e.message
        } catch (e: HttpException) {
            return e.message
        }
        return if (request.isSuccessful && request.body() != null) {
            request.body() ?:  ""
        }else{
            Log.e("response","Empty")
        }
    }
}