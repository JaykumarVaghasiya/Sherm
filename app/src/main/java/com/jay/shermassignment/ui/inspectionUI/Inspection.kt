package com.jay.shermassignment.ui.inspectionUI

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.jay.shermassignment.R
import com.jay.shermassignment.generic.showConfirmationDialog
import com.jay.shermassignment.generic.showCustomDialog
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
    private lateinit var overlay:LinearLayout
    private lateinit var progressBarPagination: LottieAnimationView

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
                val lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                if (!isLoading && !isLastPage && totalItemCount - 1 <= lastVisibleItemPosition) {
                    loadNextPage()
                }
            }
        })
        sortData()
    }

    private fun initializeViews() {
        recyclerView = findViewById(R.id.rvInspection)
        recyclerView.overScrollMode = View.OVER_SCROLL_ALWAYS
        overlay=findViewById(R.id.overLay)!!
        progressBarPagination = findViewById(R.id.pbPagination)
        overlay.visibility = View.VISIBLE
        overlay.bringToFront()
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
        isLoading = true
        if (currentPage == 0) {
            progressBarPagination.visibility = View.GONE
        } else {

            overlay.visibility = View.GONE
            progressBarPagination.visibility = View.VISIBLE
        }
        currentPage++

        val authToken = SessionManager(this).fetchAuthToken()
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
                    showConfirmationDialog(getString(R.string.sherm),getString(R.string.empty_data_or_response))
                }
            } catch (e: IOException) {
                showConfirmationDialog(getString(R.string.sherm),e.message)
            } catch (e: HttpException) {
                showConfirmationDialog(getString(R.string.sherm),e.message)
            } finally {
                isLoading = false
                progressBarPagination.visibility = View.GONE
                overlay.visibility = View.GONE
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val authToken = SessionManager(this).fetchAuthToken()
        overlay.visibility = View.VISIBLE

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
                    showConfirmationDialog(getString(R.string.sherm),getString(R.string.empty_data_or_response))
                }
            } catch (e: IOException) {
                showConfirmationDialog(getString(R.string.sherm),e.message)
            } catch (e: HttpException) {
                showConfirmationDialog(getString(R.string.sherm),e.message)
            } finally {
                isLoading = false
                progressBarPagination.visibility = View.GONE
                overlay.visibility = View.GONE
            }
        }
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
                    showCustomDialog(
                        R.string.sherm,
                        R.string.deleteThis
                    ) {
                        inspectionAdapter.deleteInspection(row)
                    }
                } else {
                    showConfirmationDialog(getString(R.string.sherm),getString( R.string.cantdeleted)
                    )
                }
            } catch (e: IOException) {
                showConfirmationDialog(getString(R.string.sherm),e.message)
            } catch (e: HttpException) {
                showConfirmationDialog(getString(R.string.sherm),e.message)
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
