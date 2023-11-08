package com.jay.shermassignment.ui.inspectionUI

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jay.shermassignment.R
import com.jay.shermassignment.databinding.ActivityInspectionBinding
import com.jay.shermassignment.di.viewmodels.InspectionViewModel
import com.jay.shermassignment.generic.showConfirmationDialog
import com.jay.shermassignment.generic.showCustomDialog
import com.jay.shermassignment.generic.startActivityStart
import com.jay.shermassignment.model.inspection.Row
import com.jay.shermassignment.pagination.InspectionPagingAdapter
import com.jay.shermassignment.ui.inspectionDetailsUI.AddInspectionActivity
import com.jay.shermassignment.ui.inspectionDetailsUI.ShowInspectionDetailsActivity
import com.jay.shermassignment.utils.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
@AndroidEntryPoint
class Inspection : AppCompatActivity(), InspectionPagingAdapter.OnInspectionListener,
    InspectionPagingAdapter.OnDeleteListener {

    lateinit var inspectionAdapter: InspectionPagingAdapter
    lateinit var _binding :ActivityInspectionBinding
    lateinit var inspectionViewModel: InspectionViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding= ActivityInspectionBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        supportActionBar?.setTitle(R.string.inspections)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        inspectionAdapter = InspectionPagingAdapter(this, this)
        inspectionViewModel=ViewModelProvider(this)[InspectionViewModel::class.java]
        val inspectionLayoutManager = LinearLayoutManager(this)
        _binding.rvInspection.layoutManager = inspectionLayoutManager
        _binding.rvInspection.adapter = inspectionAdapter

        inspectionViewModel.list.observe(this, Observer {
            inspectionAdapter.submitData(lifecycle,it)
        })


    }



    private fun setupRecyclerView() {

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
