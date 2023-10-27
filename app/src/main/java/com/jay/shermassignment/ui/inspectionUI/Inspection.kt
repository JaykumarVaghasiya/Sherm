package com.jay.shermassignment.ui.inspectionUI

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.jay.shermassignment.R
import com.jay.shermassignment.generic.showToast
import com.jay.shermassignment.generic.startActivityStart
import com.jay.shermassignment.response.inspection.InspectionRef
import com.jay.shermassignment.response.inspection.Row
import com.jay.shermassignment.ui.inspectionDetailsUI.AddInspectionActivity
import com.jay.shermassignment.ui.inspectionDetailsUI.ShowInspectionDetailsActivity
import com.jay.shermassignment.utils.SessionManager
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class Inspection : AppCompatActivity(), InspectionAdapter.OnInspectionListener, InspectionAdapter.OnDeleteListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var inspectionAdapter: InspectionAdapter
    private lateinit var progressBar: LottieAnimationView
    private lateinit var progressBarPagination: LottieAnimationView

    private val visibleThreshold = 1
    private var isLoading = false
    private var isLastPage = false
    private var currentPage = 0
    private var totalRecords = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inspection)
        supportActionBar?.setTitle(R.string.inspections)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initializeViews()
        setupRecyclerView()


        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                if (!isLoading && !isLastPage && totalItemCount - 1 <= lastVisibleItemPosition + visibleThreshold) {
                    loadNextPage()
                }
            }
        })
        sortData()
    }

    private fun initializeViews() {
        progressBar = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.rvInspection)
        recyclerView.overScrollMode = View.OVER_SCROLL_ALWAYS
        progressBarPagination = findViewById(R.id.pbPagination)
        progressBar.visibility = View.VISIBLE
        progressBarPagination.visibility = View.GONE
    }

    private fun setupRecyclerView() {
        inspectionAdapter = InspectionAdapter(this, this, this)
        val inspectionLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = inspectionLayoutManager
        recyclerView.adapter = inspectionAdapter

    }

    private fun sortData() {
        inspectionAdapter.sortInspectionListDescending()
    }

    private fun loadNextPage() {
        if (isLoading || isLastPage) {
            return
        }

        val authToken = SessionManager(this).fetchAuthToken()
        isLoading = true

        if (currentPage == 0) {

            progressBar.visibility = View.VISIBLE
            progressBarPagination.visibility = View.GONE
        } else {

            progressBar.visibility = View.GONE
            progressBarPagination.visibility = View.VISIBLE
        }
        currentPage++

        lifecycleScope.launch {
            try {
                val inspectionResponse = InspectionDetailsInstance.api.getInspectionDetails(
                    InspectionRef(
                        inspectionId = "",
                        inspectionLocation = "",
                        page = currentPage,
                        responsibleId = null,
                        sidx = "inspectionId",
                        siteId = arrayListOf(),
                        sord = "desc",
                        status = "false"
                    ), "Bearer $authToken"
                )

                if (inspectionResponse.isSuccessful && inspectionResponse.body() != null) {
                    val rows = inspectionResponse.body()?.content?.rows ?: emptyList()
                    val result = inspectionResponse.body()
                    totalRecords = result?.content?.records ?: totalRecords

                    if (rows.isEmpty()) {
                        isLastPage = true
                    }

                    inspectionAdapter.submitInspectionList(rows)
                } else {
                    showToast(getString(R.string.empty_data_or_response))
                }
            } catch (e: IOException) {
                showToast(e.message)
            } catch (e: HttpException) {
                showToast(e.message)
            } finally {
                isLoading = false
                progressBarPagination.visibility = View.GONE
                progressBar.visibility = View.GONE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadNextPage()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.ispection_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> {
                startActivityStart<AddInspectionActivity>()
            }
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDeleteClicked(row: Row) {
        val authToken = SessionManager(this).fetchAuthToken()
        lifecycleScope.launch {
            try {
                val queryParameters = row.id
                val inspectionResponse = InspectionDetailsInstance.api.deleteInspectionItem(
                    queryParameters,
                    "Bearer $authToken"
                )
                if (inspectionResponse.isSuccessful) {
                    inspectionAdapter.deleteInspection(row)
                } else {
                    showToast(getString(R.string.cantdeleted))
                }
            } catch (e: IOException) {
                showToast(e.message)
            } catch (e: HttpException) {
                showToast(e.message)
            }
        }
    }

    override fun onInspectionClicked(row: Row) {
        val intent = Intent(this, ShowInspectionDetailsActivity::class.java)
        intent.putExtra("inspectionId", row.inspectionId)
        intent.putExtra("id", row.id)
        startActivity(intent)
    }
}
