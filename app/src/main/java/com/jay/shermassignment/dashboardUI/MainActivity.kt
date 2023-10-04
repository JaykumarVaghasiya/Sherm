package com.jay.shermassignment.dashboardUI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jay.shermassignment.inspectionUI.Inspection
import com.jay.shermassignment.R
import com.jay.shermassignment.login.LoginActivity
import com.jay.shermassignment.model.dashboard.Dashboard

class MainActivity : AppCompatActivity() {

    private lateinit var dashboardAdapter: DashboardAdapter

    private val dashboardList= listOf(
       Dashboard("hazard","HAZARDS"),
       Dashboard("incident","INCIDENTS"),
       Dashboard("issues","ISSUES"),
       Dashboard("inspection","INSPECTIONS"),
       Dashboard("document","DOCUMENTS"),
       Dashboard("icon_sds","SDS"),
       Dashboard("icon_mytraining","MY TRAINING"),
       Dashboard("icon_mycat","MY CAT"),
       Dashboard("licence","LICENCES"),
       Dashboard("certificate","CERTIFICATES")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView=findViewById<RecyclerView>(R.id.rvDashboard)

        val layoutManager = GridLayoutManager(this,2)
        recyclerView.layoutManager = layoutManager

        dashboardAdapter = DashboardAdapter(this, dashboardList){dashboard ->
            if(dashboard.name == "INSPECTIONS"){
                val intent=Intent(this, Inspection::class.java)
                startActivity(intent)
            }
        }
        recyclerView.adapter = dashboardAdapter
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.overflow_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.signOut) {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, R.string.sign_out, Toast.LENGTH_SHORT).show()
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}