package com.jay.shermassignment.ui.correctiveaction

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jay.shermassignment.R
import com.jay.shermassignment.model.correctiveaction.CorrectiveActionData
import com.jay.shermassignment.model.correctiveaction.Row
import com.jay.shermassignment.ui.correctiveactiondetails.CAViewActivity
import com.jay.shermassignment.ui.correctiveevaluation.CorrectiveEvaluation
import com.jay.shermassignment.utils.SessionManager
import kotlinx.coroutines.launch

class CorrectiveAction : AppCompatActivity(), CorrectiveActionAdapter.OnCorrectiveActionItemClick,
    CorrectiveActionAdapter.OnCorrectiveEvaluationClick {

    private lateinit var correctiveActionRecyclerView: RecyclerView
    private lateinit var correctiveActionAdapter: CorrectiveActionAdapter
    private lateinit var progressBar: ProgressBar

    private lateinit var authToken: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_corrective_action)
        supportActionBar?.setTitle("${R.string.corrective_action}+ ( )")
        setupViews()
        setupAdapter()
        setupListeners()
        authToken = SessionManager(this).fetchAuthToken().toString()
        loadCorrectiveAction(authToken)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.ispection_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> openAddCorrectiveActionView()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupViews() {
        correctiveActionRecyclerView = findViewById(R.id.rvCorrectiveAction)
        progressBar = findViewById(R.id.pbCorrectiveAction)
        progressBar.bringToFront()
        progressBar.visibility = View.VISIBLE
    }

    private fun setupAdapter() {
        correctiveActionAdapter = CorrectiveActionAdapter(this, this, this)
        val inspectionLayoutManager = LinearLayoutManager(this)
        correctiveActionRecyclerView.layoutManager = inspectionLayoutManager
        correctiveActionRecyclerView.adapter = correctiveActionAdapter
    }

    private fun setupListeners() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }

    private fun loadCorrectiveAction(authToken: String) {
        lifecycleScope.launch {
            try {
                val correctiveActionResponse = CorrectiveActionInstance.api.getAllCorrectiveAction(
                    CorrectiveActionData(2888, 1, "action", "asc", "Risk"),
                    "Bearer $authToken"
                )

                if (correctiveActionResponse.isSuccessful && correctiveActionResponse.body() != null) {
                    correctiveActionAdapter.submitInspectionList(correctiveActionResponse.body()!!.content.rows)
                    progressBar.visibility = View.GONE
                } else {
                    showToast(getString(R.string.empty_data_or_response), Toast.LENGTH_LONG)
                    progressBar.visibility = View.GONE
                }
            } catch (e: Exception) {
                showToast(e.message, Toast.LENGTH_SHORT)
            }
        }
    }

    private fun openAddCorrectiveActionView() {
        val intent = Intent(this, CAViewActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)

    }

    override fun onItemClick(row: Row) {
        val id = row.id
        val intent = Intent(this, CAViewActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        intent.putExtra("correctiveActionId", id)
        startActivity(intent)
    }

    override fun onCorrectiveEvaluationClick(row: Row) {
        val intent = Intent(this, CorrectiveEvaluation::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        intent.putExtra("correctiveActionId", row.id)
        startActivity(intent)
    }

    private fun showToast(message: String?, duration: Int) {
        Toast.makeText(this, message, duration).show()
    }
}
