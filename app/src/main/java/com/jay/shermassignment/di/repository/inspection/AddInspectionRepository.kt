package com.jay.shermassignment.di.repository.inspection

import android.content.Context
import android.util.Log
import com.jay.shermassignment.dataModel.addInspectionData.AddInspectionRef
import com.jay.shermassignment.ui.inspectionDetailsUI.ShowInspectionDetailsInstance
import com.jay.shermassignment.utils.SessionManager
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddInspectionRepository @Inject constructor(@ApplicationContext context: Context) {
    val authToken=SessionManager(context).fetchAuthToken()

    suspend fun addInspectionToList(addInspectionRef: AddInspectionRef):Any?{

        val addInspectionResponse=try {
            ShowInspectionDetailsInstance.api.getAddInspectionData(addInspectionRef,"Bearer $authToken")
        }catch (e:Exception){
            return e.message
        }catch (e:IOException){
            return e.message
        }catch (e:HttpException){
            return e.message
        }
        return if(addInspectionResponse.isSuccessful && addInspectionResponse.body() != null){
            addInspectionResponse.body() ?: ""
        }else{
            Log.e("response","Empty")
        }
    }
}