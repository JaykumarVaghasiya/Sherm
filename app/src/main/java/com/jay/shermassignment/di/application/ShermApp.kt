package com.jay.shermassignment.di.application

import android.app.Application
import com.jay.shermassignment.di.repository.login.UserRepository
import com.jay.shermassignment.utils.SessionManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ShermApp : Application() {
    companion object {

        lateinit var loginRepository: UserRepository
            private set
    }



    override fun onCreate() {
        super.onCreate()
        loginRepository = UserRepository(SessionManager(applicationContext))



    }
}