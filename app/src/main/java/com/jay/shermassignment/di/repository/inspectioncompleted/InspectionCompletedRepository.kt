package com.jay.shermassignment.di.repository.inspectioncompleted

import android.content.Context
import android.util.Log
import com.jay.shermassignment.dataModel.addinspectioncompletted.CompletedInspectionBody
import com.jay.shermassignment.ui.add_inspection_completed.CompletedInstance
import com.jay.shermassignment.utils.SessionManager
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InspectionCompletedRepository @Inject constructor(@ApplicationContext context: Context) {
val authToken=SessionManager(context).fetchAuthToken()

    suspend fun isInspectionCompleted(completedInspectionBody: CompletedInspectionBody):Any?{
        val isCompleted=try {
            CompletedInstance.api.completedScheduledInspection(completedInspectionBody,"Bearer $authToken")
        }catch (e:Exception){
            return e.message
        }catch (e:IOException){
            return e.message
        }catch (e:HttpException){
            return e.message
        }
        return if(isCompleted.isSuccessful && isCompleted.body() != null){
            isCompleted.body() ?: ""
        }else{
            Log.e("response","EMPTY")
        }

    }
}