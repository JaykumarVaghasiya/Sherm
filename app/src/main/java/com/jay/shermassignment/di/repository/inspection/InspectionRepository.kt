package com.jay.shermassignment.di.repository.inspection

import android.content.Context
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.jay.shermassignment.pagination.InspectionPagingSource
import com.jay.shermassignment.ui.inspectionUI.InspectionApi
import com.jay.shermassignment.ui.inspectionUI.InspectionDetailsInstance
import com.jay.shermassignment.utils.SessionManager
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton

class InspectionRepository @Inject constructor(val inspectionAPI:InspectionApi, @ApplicationContext val context: Context) {
    val authToken = SessionManager(context).fetchAuthToken()
    fun getInspectionList()=Pager(
        config = PagingConfig(pageSize = 4, maxSize = 12),
        pagingSourceFactory = {InspectionPagingSource(inspectionAPI,context)}
    ).liveData

    suspend fun deleteInspection(id: Int):Any?{
        val deleteResponse=try {
            InspectionDetailsInstance.api.deleteInspectionItem(id,"Bearer $authToken")
        }catch (e:Exception){
           return e.message
        }catch (e: IOException){
           return e.message
        }catch (e:HttpException){
           return e.message
        }
        return if(deleteResponse.isSuccessful && deleteResponse.body() != null){
            return deleteResponse.body() ?: ""
        }else{
            Log.d("response","Empty")
        }
    }
}