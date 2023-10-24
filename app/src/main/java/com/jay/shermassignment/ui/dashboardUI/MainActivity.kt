package com.jay.shermassignment.ui.dashboardUI

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jay.shermassignment.R
import com.jay.shermassignment.generic.startActivityStart
import com.jay.shermassignment.response.dashboard.Dashboard
import com.jay.shermassignment.ui.inspectionUI.Inspection
import com.jay.shermassignment.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {

    private lateinit var dashboardAdapter: DashboardAdapter

    private val dashboardList = listOf(
        Dashboard("hazard", "HAZARDS"),
        Dashboard("incident", "INCIDENTS"),
        Dashboard("issues", "ISSUES"),
        Dashboard("inspection", "INSPECTIONS"),
        Dashboard("document", "DOCUMENTS"),
        Dashboard("icon_sds", "SDS"),
        Dashboard("icon_mytraining", "MY TRAINING"),
        Dashboard("icon_mycat", "MY CAT"),
        Dashboard("licence", "LICENCES"),
        Dashboard("certificate", "CERTIFICATES")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setTitle(R.string.dashboard)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.rvDashboard)
        recyclerView.layoutManager = object : GridLayoutManager(this, 2) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }

        dashboardAdapter = DashboardAdapter(this, dashboardList) { dashboard ->
            if (dashboard.name == "INSPECTIONS") {
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivityStart<Inspection>()
            }

        }
        recyclerView.adapter = dashboardAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.overflow_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.signOut -> {
                startActivityStart<LoginActivity>()
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                Toast.makeText(this, R.string.sign_out, Toast.LENGTH_SHORT).show()
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}