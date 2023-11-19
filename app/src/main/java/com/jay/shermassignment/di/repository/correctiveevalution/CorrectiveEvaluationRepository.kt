package com.jay.shermassignment.di.repository.correctiveevalution

import android.content.Context
import android.util.Log
import com.jay.shermassignment.ui.corrective_evaluation.CorrectiveEvaluationBody
import com.jay.shermassignment.ui.corrective_evaluation.CorrectiveEvaluationInstance
import com.jay.shermassignment.utils.SessionManager
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CorrectiveEvaluationRepository @Inject constructor(@ApplicationContext context: Context) {

    val authToken = SessionManager(context).fetchAuthToken()

    suspend fun correctiveEvaluation(correctiveEvaluationBody: CorrectiveEvaluationBody): Any? {

        val correctiveEvaluation = try {
            CorrectiveEvaluationInstance.api.addEvaluation(
                correctiveEvaluationBody,
                "Bearer $authToken"
            )
        } catch (e: Exception) {
            return e.message
        } catch (e: IOException) {
            return e.message
        } catch (e: HttpException) {
            return e.message
        }

        return if (correctiveEvaluation.isSuccessful && correctiveEvaluation.body() != null) {
            correctiveEvaluation.body() ?: ""
        } else {
            Log.e("response", "Empty")
        }

    }
}