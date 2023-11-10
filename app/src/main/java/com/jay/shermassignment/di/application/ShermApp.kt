package com.jay.shermassignment.di.application

import android.app.Application
import com.jay.shermassignment.di.repository.inspection.AddInspectionRepository
import com.jay.shermassignment.di.repository.inspection.InspectionDetailRepository
import com.jay.shermassignment.di.repository.login.UserRepository
import com.jay.shermassignment.di.repository.spinner.InspectionLocationRepository
import com.jay.shermassignment.di.repository.spinner.InspectionTypeRepository
import com.jay.shermassignment.di.repository.spinner.ReportingToRepository
import com.jay.shermassignment.di.repository.spinner.ResponsiblePersonRepository
import com.jay.shermassignment.utils.SessionManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ShermApp : Application() {
    companion object {

        lateinit var loginRepository: UserRepository
            private set

        lateinit var inspectionDetailRepository: InspectionDetailRepository
            private set

        lateinit var inspectionTypeRepository: InspectionTypeRepository
            private set

        lateinit var inspectionLocationRepository: InspectionLocationRepository
            private set

        lateinit var responsiblePersonRepository: ResponsiblePersonRepository
            private set

        lateinit var reportingToRepository: ReportingToRepository
            private set

        lateinit var addInspectionRepository: AddInspectionRepository
            private set
    }



    override fun onCreate() {
        super.onCreate()
        loginRepository = UserRepository(SessionManager(applicationContext))
        inspectionDetailRepository = InspectionDetailRepository(applicationContext)
        inspectionTypeRepository = InspectionTypeRepository(applicationContext)
        inspectionLocationRepository = InspectionLocationRepository(applicationContext)
        responsiblePersonRepository = ResponsiblePersonRepository(applicationContext)
        reportingToRepository = ReportingToRepository(applicationContext)
        addInspectionRepository=AddInspectionRepository(applicationContext)
    }
}