package com.jay.shermassignment.ui.inspectionUI

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jay.shermassignment.R
import com.jay.shermassignment.dataModel.inspection.Row
import com.jay.shermassignment.databinding.ActivityInspectionBinding
import com.jay.shermassignment.di.viewmodels.Inspection.DeleteInspectionViewModel
import com.jay.shermassignment.di.viewmodels.Inspection.InspectionViewModel
import com.jay.shermassignment.generic.showCustomDialog
import com.jay.shermassignment.generic.startActivityStart
import com.jay.shermassignment.pagination.InspectionPagingAdapter
import com.jay.shermassignment.pagination.LoaderAdapter
import com.jay.shermassignment.ui.inspectionDetailsUI.AddInspectionActivity
import com.jay.shermassignment.ui.inspectionDetailsUI.ShowInspectionDetailsActivity
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class Inspection : AppCompatActivity(), InspectionPagingAdapter.OnInspectionListener,
    InspectionPagingAdapter.OnDeleteListener {

    private lateinit var inspectionAdapter: InspectionPagingAdapter
    private lateinit var _binding: ActivityInspectionBinding
    private lateinit var inspectionViewModel: InspectionViewModel
    private lateinit var deleteInspectionViewModel: DeleteInspectionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityInspectionBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        supportActionBar?.setTitle(R.string.inspections)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        _binding.overLay.loadingScreen.bringToFront()
        setupRecyclerView()
        callViewModel()
    }

    override fun onResume() {
        super.onResume()
        inspectionViewModel.list.observe(this){
            inspectionAdapter.submitData(lifecycle,it)
        }
    }

    private fun callViewModel() {

        inspectionViewModel = ViewModelProvider(this)[InspectionViewModel::class.java]
        deleteInspectionViewModel = ViewModelProvider(this)[DeleteInspectionViewModel::class.java]
        inspectionViewModel.list.observe(this) {
            _binding.overLay.loadingScreen.visibility = View.GONE
            inspectionAdapter.submitData(lifecycle, it)
        }
    }

    private fun setupRecyclerView() {
        _binding.overLay.loadingScreen.visibility = View.VISIBLE
        inspectionAdapter = InspectionPagingAdapter(this, this)
        val inspectionLayoutManager = LinearLayoutManager(this)
        _binding.rvInspection.layoutManager = inspectionLayoutManager
        _binding.rvInspection.setHasFixedSize(true)
        _binding.rvInspection.adapter = inspectionAdapter.withLoadStateHeaderAndFooter(
            footer = LoaderAdapter(),
            header = LoaderAdapter()
        )

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
        deleteInspectionViewModel.deleteInspection(row.id)
        deleteInspectionViewModel.deleteInspectionLiveData.observe(this) {
            showCustomDialog(getString(R.string.sherm),getString(R.string.are_you_sure)) {
                inspectionAdapter.deleteInspection(row)
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
