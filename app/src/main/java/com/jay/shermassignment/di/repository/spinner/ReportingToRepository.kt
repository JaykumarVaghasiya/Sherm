package com.jay.shermassignment.di.repository.spinner

import android.content.Context
import android.util.Log
import com.jay.shermassignment.api.inspection.SpinnerInstance
import com.jay.shermassignment.utils.SessionManager
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReportingToRepository @Inject constructor(@ApplicationContext val context: Context) {
    val authToken = SessionManager(context).fetchAuthToken()

    suspend fun getReportingTo(): Any? {
        val reportingToResponse = try {
            SpinnerInstance.api.getAlLResponsiblePerson("Bearer $authToken")
        } catch (e: IOException) {
            return e.message
        } catch (e: Exception) {
            return e.message
        } catch (e: HttpException) {
            return e.message
        }

        return if (reportingToResponse.isSuccessful && reportingToResponse.body() != null) {
            reportingToResponse.body() ?: ""
        } else {
            Log.d("response", "Empty")
        }
    }

}