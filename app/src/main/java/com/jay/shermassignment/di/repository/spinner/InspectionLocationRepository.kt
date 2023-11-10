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
class InspectionLocationRepository @Inject constructor(@ApplicationContext context: Context) {
    val authToken = SessionManager(context).fetchAuthToken()

    suspend fun getInspectionLocation(categoryId: Int, siteId: Int, inspectionTypeId: Int): Any? {
        val inspectionLocationResponse = try {
            SpinnerInstance.api.getLocationFromCatInsTypeSite(
                catId = categoryId,
                inspectionTypeId = inspectionTypeId,
                siteId = siteId,
                authToken = "Bearer $authToken"
            )
        } catch (e: Exception) {
            return e.message
        } catch (e: IOException) {
            return e.message
        } catch (e: HttpException) {
            return e.message
        }catch (e:IllegalAccessError){
            return e.message
        }

        return if (inspectionLocationResponse.isSuccessful && inspectionLocationResponse.body() != null) {
            inspectionLocationResponse.body()?:""
        } else {
            Log.d("response", "Empty")
        }
    }
}