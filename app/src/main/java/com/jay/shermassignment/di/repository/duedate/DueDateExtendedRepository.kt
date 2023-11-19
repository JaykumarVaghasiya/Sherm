package com.jay.shermassignment.di.repository.duedate

import android.content.Context
import android.util.Log
import com.jay.shermassignment.dataModel.approveca.ApproveCABody
import com.jay.shermassignment.ui.dueDate.DueDateInstance
import com.jay.shermassignment.utils.SessionManager
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DueDateExtendedRepository @Inject constructor(@ApplicationContext context: Context) {

    val authToken = SessionManager(context).fetchAuthToken()

    suspend fun approveDueDate(approveCABody: ApproveCABody): Any? {
        val approve = try {
            DueDateInstance.api.getDueDateApproval(approveCABody, "Bearer $authToken")
        } catch (e: Exception) {
            return e.message
        } catch (e: IOException) {
            return e.message
        } catch (e: HttpException) {
            return e.message
        }
        return if (approve.isSuccessful && approve.body() != null) {
            approve.body() ?: ""
        } else {
            Log.e("response", "Empty")
        }
    }

    suspend fun rejectDueDate(rejectCABody: ApproveCABody): Any? {
        val reject = try {
            DueDateInstance.api.getDueDateApproval(rejectCABody, "Bearer $authToken")
        } catch (e: Exception) {
            return e.message
        } catch (e: IOException) {
            return e.message
        } catch (e: HttpException) {
            return e.message
        }
        return if (reject.isSuccessful && reject.body() != null) {
            reject.body() ?: ""
        } else {
            Log.e("response", "Empty")
        }
    }
}