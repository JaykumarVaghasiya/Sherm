package com.jay.shermassignment.pagination

import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jay.shermassignment.model.inspection.InspectionRef
import com.jay.shermassignment.model.inspection.Row
import com.jay.shermassignment.ui.inspectionUI.InspectionApi
import com.jay.shermassignment.utils.SessionManager

class InspectionPagingSource(val inspectionApi: InspectionApi,val context: Context) :
    PagingSource<Int, Row>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Row> {
       return try {
            val position = params.key ?: 1
            val authToken = SessionManager(context).fetchAuthToken()
            val body = InspectionRef(
                inspectionId = "",
                inspectionLocation = "",
                page = position,
                responsibleId = null,
                sidx = "inspectionId",
                siteId = arrayListOf(),
                sord = "desc",
                status = "false"
            )
            val response = inspectionApi.getInspectionDetails(body, "Bearer $authToken")
            return LoadResult.Page(
                data = response.body()?.content!!.rows,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == response.body()!!.content.total) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Row>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}