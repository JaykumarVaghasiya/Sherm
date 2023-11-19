package com.jay.shermassignment.di.repository.correctiveaction

import android.content.Context
import android.util.Log
import com.jay.shermassignment.dataModel.correctiveaction.CorrectiveActionData
import com.jay.shermassignment.ui.corrective_action.CorrectiveActionInstance
import com.jay.shermassignment.utils.SessionManager
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CorrectiveActionRepository @Inject constructor(@ApplicationContext context: Context) {
    val authToken = SessionManager(context).fetchAuthToken()

    suspend fun getAllCorrectiveActionList(id: Int): Any? {

        val correctiveActionResponse = try {
            CorrectiveActionInstance.api.getAllCorrectiveAction(
                CorrectiveActionData(
                    id,
                    1,
                    "id",
                    "desc",
                    "Inspection"
                ), "Bearer $authToken"
            )
        } catch (e: Exception) {
            return e.message
        } catch (e: IOException) {
            return e.message
        } catch (e: HttpException) {
            return e.message
        }
        return if (correctiveActionResponse.isSuccessful && correctiveActionResponse.body() != null) {
            correctiveActionResponse.body() ?: ""
        } else {
            Log.e("response", "Empty")
        }
    }
}