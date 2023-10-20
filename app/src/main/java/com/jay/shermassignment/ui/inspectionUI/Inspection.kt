package com.jay.shermassignment.ui.inspectionUI

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jay.shermassignment.R
import com.jay.shermassignment.generic.BackCallBack
import com.jay.shermassignment.generic.showToast
import com.jay.shermassignment.generic.startActivityStart
import com.jay.shermassignment.model.inspection.InspectionRef
import com.jay.shermassignment.model.inspection.Row
import com.jay.shermassignment.ui.inspectionDetailsUI.AddInspectionActivity
import com.jay.shermassignment.ui.inspectionDetailsUI.ShowInspectionDetailsActivity
import com.jay.shermassignment.utils.SessionManager
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class Inspection : AppCompatActivity(), InspectionAdapter.OnInspectionListener,
    InspectionAdapter.OnDeleteListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var inspectionAdapter: InspectionAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var progressBarPagination: ProgressBar

    private val visibleThreshold = 5
    private var isLoading = false
    private var isLastPage = false
    private var currentPage = 1
    private var totalRecords = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inspection)
        supportActionBar?.setTitle(R.string.inspections)
        initializeViews()
        setupRecyclerView()
        setupBackButton()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                if (!isLoading && !isLastPage &&
                    totalItemCount <= (lastVisibleItemPosition + visibleThreshold)
                ) {
                    loadMoreData()
                }
            }
        })

        loadInspectionData()
    }

    private fun initializeViews() {
        progressBar = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.rvInspection)
        progressBarPagination = findViewById(R.id.pbPagination)
    }

    private fun setupRecyclerView() {
        inspectionAdapter = InspectionAdapter(this, this, this)
        val inspectionLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = inspectionLayoutManager
        recyclerView.adapter = inspectionAdapter
    }

    private fun loadInspectionData() {
        val authToken = SessionManager(this).fetchAuthToken()
        progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            val inspectionResponse = try {
                InspectionDetailsInstance.api.getInspectionDetails(
                    InspectionRef(
                        inspectionId = "",
                        inspectionLocation = "",
                        page = currentPage,
                        responsibleId = null,
                        sidx = "completedDate",
                        siteId = arrayListOf(),
                        sord = "asc",
                        status = "false"
                    ), "Bearer $authToken"
                )
            } catch (e: IOException) {
                showToast(this@Inspection, e.message)
                return@launch
            } catch (e: HttpException) {
                showToast(this@Inspection, e.message)
                return@launch
            }

            if (inspectionResponse.isSuccessful && inspectionResponse.body() != null) {
                val rows = inspectionResponse.body()?.content?.rows ?: emptyList()
                val result = inspectionResponse.body()
                totalRecords = result?.content?.records ?: totalRecords
                inspectionAdapter.submitInspectionList(rows)
                progressBar.visibility = View.GONE
            } else {
                showToast(this@Inspection, getString(R.string.empty_data_or_response))
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun loadMoreData() {
        if (isLoading) {
            return
        }

        val authToken = SessionManager(this).fetchAuthToken()
        isLoading = true
        progressBarPagination.visibility = View.VISIBLE

        lifecycleScope.launch {
            val inspectionResponse = try {
                currentPage++
                InspectionDetailsInstance.api.getInspectionDetails(
                    InspectionRef(
                        inspectionId = "",
                        inspectionLocation = "",
                        page = currentPage,
                        responsibleId = null,
                        sidx = "completedDate",
                        siteId = arrayListOf(),
                        sord = "asc",
                        status = "false"
                    ), "Bearer $authToken"
                )
            } catch (e: IOException) {
                showToast(this@Inspection, e.message)
                return@launch
            } catch (e: HttpException) {
                showToast(this@Inspection, e.message)
                return@launch
            } finally {
                isLoading = false
                progressBarPagination.visibility = View.GONE
            }

            if (inspectionResponse.isSuccessful && inspectionResponse.body() != null) {
                val rows = inspectionResponse.body()?.content?.rows ?: emptyList()
                val result = inspectionResponse.body()
                totalRecords = result?.content?.records ?: totalRecords
                inspectionAdapter.submitInspectionList(rows)
            } else {
                showToast(this@Inspection, getString(R.string.empty_data_or_response))
            }
        }
    }

    private fun setupBackButton() {
        val onBackPressedCallback = BackCallBack {
            finish()
        }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.ispection_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> {
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivityStart<AddInspectionActivity>()

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDeleteClicked(row: Row) {
        val authToken = SessionManager(this).fetchAuthToken()
        lifecycleScope.launch {
            val inspectionResponse = try {
                val queryParameters = row.id
                InspectionDetailsInstance.api.deleteInspectionItem(
                    queryParameters,
                    "Bearer $authToken"
                )
            } catch (e: IOException) {
                showToast(this@Inspection, e.message)
                return@launch
            } catch (e: HttpException) {
                showToast(this@Inspection, e.message)
                return@launch
            }
            if (inspectionResponse.isSuccessful) {
                inspectionAdapter.deleteInspection(row)
            } else {
                showToast(this@Inspection, R.string.cantdeleted.toString())
            }
        }
    }

    override fun onInspectionClicked(row: Row) {
        val intent = Intent(this, ShowInspectionDetailsActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        intent.putExtra("id", row.id)
        startActivity(intent)
    }
}
