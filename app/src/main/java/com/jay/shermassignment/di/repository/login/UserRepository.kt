package com.jay.shermassignment.di.repository.login

import com.jay.shermassignment.ui.login.UserDetails
import com.jay.shermassignment.ui.login.UserDetailsInstance
import com.jay.shermassignment.utils.SessionManager
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    val sessionManager: SessionManager
) {
    suspend fun getUserDetails(user: UserDetails): Any? {
        val response = try {
            UserDetailsInstance.api.getUserDetails(user)
        } catch (e: IOException) {
            return e.message
        } catch (e: HttpException) {
            return e.message
        }catch (e:Exception){
            return e.message
        }
        if (response.isSuccessful && response.body() != null) {
            val userResponse = response.body()
            userResponse?.content?.token?.let { sessionManager.saveAuthToken(it) }
        }
        return response.body()?: ""
    }
}

