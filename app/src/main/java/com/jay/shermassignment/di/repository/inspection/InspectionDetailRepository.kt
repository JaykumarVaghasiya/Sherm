package com.jay.shermassignment.di.repository.inspection

import android.content.Context
import com.jay.shermassignment.ui.inspectionDetailsUI.ShowInspectionDetailsInstance
import com.jay.shermassignment.utils.SessionManager
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InspectionDetailRepository @Inject constructor(
    @ApplicationContext val context: Context
) {
    val authToken = SessionManager(context).fetchAuthToken()
    suspend fun getAllInspectionDetail(id:Int): Any? {
        val inspectionResponse = try {
            ShowInspectionDetailsInstance.api.getShowInspectionDetails(id, "Bearer $authToken")
        } catch (e: Exception) {
            return e.message
        } catch (e: IOException) {
            return e.message
        } catch (e: HttpException) {
            return e.message
        }
        return if(inspectionResponse.isSuccessful && inspectionResponse.body() != null)
            inspectionResponse.body()?: ""
        else TODO()
    }
}