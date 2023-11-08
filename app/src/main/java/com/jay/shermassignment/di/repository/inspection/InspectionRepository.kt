package com.jay.shermassignment.di.repository.inspection

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.jay.shermassignment.pagination.InspectionPagingSource
import com.jay.shermassignment.ui.inspectionUI.InspectionApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InspectionRepository @Inject constructor(val inspectionAPI:InspectionApi, val context: Context) {

    fun getInspectionList()=Pager(
        config = PagingConfig(pageSize = 10, maxSize = 10),
        pagingSourceFactory = {InspectionPagingSource(inspectionAPI,context)}
    ).liveData
}