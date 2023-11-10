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
class ResponsiblePersonRepository @Inject constructor(@ApplicationContext val context: Context) {
    val authToken=SessionManager(context).fetchAuthToken()

    suspend fun getResponsiblePerson(siteId:Int):Any?{
        val responsiblePersonResponse =try {
            SpinnerInstance.api.getResponsiblePersonBySite(siteId,"Bearer $authToken")
        }catch (e:Exception){
            return e.message
        }catch (e:IOException){
            return e.message
        }catch (e:HttpException){
            return e.message
        }
        return if(responsiblePersonResponse.isSuccessful && responsiblePersonResponse.body() != null){
            responsiblePersonResponse.body() ?: ""
        }else{
            Log.d("response","Empty")
        }
    }
}