package com.jay.shermassignment.di.repository.correctiveaction

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
class CorrectiveActionDetailsRepository @Inject constructor(@ApplicationContext context: Context) {

    val authToken = SessionManager(context).fetchAuthToken()

    suspend fun getCorrectiveActionDetails(id: Int): Any? {
        val getDetails = try {
            CAViewInstance.api.getAllCAViewDetails(id, "Bearer $authToken")
        } catch (e: Exception) {
            return e.message
        } catch (e: HttpException) {
            return e.message
        } catch (e: IOException) {
            return e.message
        }
        return if (getDetails.isSuccessful && getDetails.body() != null) {
            getDetails.body() ?: ""
        } else {
            Log.e("response", "Empty")
        }
    }

    suspend fun getDueDateDetails(id: Int): Any? {
        val dueDateResponse = try {
            CAViewInstance.api.checkDueDateExtend(id, "Bearer $authToken")
        } catch (e: Exception) {
            return e.message
        } catch (e: IOException) {
            return e.message
        } catch (e: HttpException) {
            return e.message
        }
        return if (dueDateResponse.isSuccessful && dueDateResponse.body() != null) {
            dueDateResponse.body() ?: ""
        } else {
            Log.e("response", "Empty")
        }
    }
}