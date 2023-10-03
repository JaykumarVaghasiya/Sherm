package com.jay.shermassignment.model.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jay.shermassignment.R
import com.jay.shermassignment.login.LoginActivity

class MainActivity : AppCompatActivity() {

    private lateinit var dashboardAdapter: DashboardAdapter

    private val dashboardList= listOf(
       Dashboard("certificate","Certificate")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView=findViewById<RecyclerView>(R.id.rvDashboard)

        val layoutManager = GridLayoutManager(this,2)
        recyclerView.layoutManager = layoutManager

        dashboardAdapter = DashboardAdapter(this, dashboardList)
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