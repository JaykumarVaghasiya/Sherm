package com.jay.shermassignment.di.application

import android.app.Application
import com.jay.shermassignment.di.repository.comment.CommentRepository
import com.jay.shermassignment.di.repository.correctiveaction.AddCorrectiveActionRepository
import com.jay.shermassignment.di.repository.correctiveaction.CorrectiveActionDetailsRepository
import com.jay.shermassignment.di.repository.correctiveaction.CorrectiveActionRepository
import com.jay.shermassignment.di.repository.correctiveevalution.CorrectiveEvaluationRepository
import com.jay.shermassignment.di.repository.duedate.CheckDueDateRepository
import com.jay.shermassignment.di.repository.duedate.DueDateExtendReviewRepository
import com.jay.shermassignment.di.repository.duedate.DueDateExtendedRepository
import com.jay.shermassignment.di.repository.duedate.DueDateRequestRepository
import com.jay.shermassignment.di.repository.inspection.AddInspectionRepository
import com.jay.shermassignment.di.repository.inspection.InspectionDetailRepository
import com.jay.shermassignment.di.repository.inspectioncompleted.InspectionCompletedRepository
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

        lateinit var correctiveActionRepository:CorrectiveActionRepository
            private set

        lateinit var addCorrectiveActionRepository: AddCorrectiveActionRepository
            private set
        lateinit var correctiveEvaluationRepository: CorrectiveEvaluationRepository
            private set

        lateinit var correctiveActionDetailsRepository: CorrectiveActionDetailsRepository
            private set

        lateinit var commentRepository:CommentRepository
            private set

        lateinit var dueDateExtendedRepository: DueDateExtendedRepository
            private set

        lateinit var dueDateRequestRepository: DueDateRequestRepository
            private set

        lateinit var checkDueDateRepository: CheckDueDateRepository
            private set

        lateinit var inspectionCompletedRepository: InspectionCompletedRepository
            private set

        lateinit var dueDateExtendReviewRepository: DueDateExtendReviewRepository
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
        inspectionCompletedRepository= InspectionCompletedRepository(applicationContext)
        checkDueDateRepository= CheckDueDateRepository(applicationContext)
        dueDateExtendedRepository= DueDateExtendedRepository(applicationContext)
        dueDateRequestRepository= DueDateRequestRepository(applicationContext)
        correctiveActionRepository= CorrectiveActionRepository(applicationContext)
        correctiveActionDetailsRepository= CorrectiveActionDetailsRepository(applicationContext)
        commentRepository= CommentRepository(applicationContext)
        inspectionCompletedRepository= InspectionCompletedRepository(applicationContext)
        addCorrectiveActionRepository= AddCorrectiveActionRepository(applicationContext)
        correctiveEvaluationRepository=CorrectiveEvaluationRepository(applicationContext)
        dueDateExtendReviewRepository=DueDateExtendReviewRepository(applicationContext)

    }
}