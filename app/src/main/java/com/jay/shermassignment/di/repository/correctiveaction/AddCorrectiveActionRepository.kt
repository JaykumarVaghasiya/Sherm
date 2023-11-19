package com.jay.shermassignment.di.repository.correctiveaction

import android.content.Context
import android.util.Log
import com.jay.shermassignment.ui.add_corrective_action.AddCorrectiveActionBody
import com.jay.shermassignment.ui.add_corrective_action.AddCorrectiveActionInstance
import com.jay.shermassignment.utils.SessionManager
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddCorrectiveActionRepository@Inject constructor(@ApplicationContext context: Context) {
    val authToken = SessionManager(context).fetchAuthToken()

    suspend fun addCorrectiveAction(addCorrectiveActionBody: AddCorrectiveActionBody):Any?{
        val addCA=try {
            AddCorrectiveActionInstance.api.addCorrectiveAction(addCorrectiveActionBody,"Bearer $authToken")
        }catch (e:Exception){
            return e.message
        }catch (e:HttpException){
            return e.message
        }catch (e:IOException){
            return e.message
        }

        return if (addCA.isSuccessful && addCA.body()!= null){
            addCA.body() ?: ""
        }else{
            Log.e("response","Empty")
        }
    }
}