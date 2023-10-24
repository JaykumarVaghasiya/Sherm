package com.jay.shermassignment.ui.corrective_action

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
import com.jay.shermassignment.response.correctiveaction.CorrectiveActionData
import com.jay.shermassignment.response.correctiveaction.Row
import com.jay.shermassignment.ui.add_corrective_action.AddCorrectiveAction
import com.jay.shermassignment.ui.corrective_action_details.CAViewActivity
import com.jay.shermassignment.ui.corrective_evaluation.CorrectiveEvaluation
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
        supportActionBar?.title = "${getString(R.string.corrective_action)}( )"
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
        val intent = Intent(this, AddCorrectiveAction::class.java)
        val iId=intent.getIntExtra("ids",0)
        intent.putExtra("iId",iId)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)

    }

    override fun onItemClick(row: Row) {
        val id = row.id
        val intent = Intent(this, CAViewActivity::class.java)
        val iId=intent.getIntExtra("ids",0)
        intent.putExtra("iId",iId)
        intent.putExtra("cId",row.id)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        intent.putExtra("correctiveActionId", id)
        startActivity(intent)
    }

    override fun onCorrectiveEvaluationClick(row: Row) {
        val intent = Intent(this, CorrectiveEvaluation::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        val iId=intent.getIntExtra("ids",0)
        intent.putExtra("iId",iId)
        intent.putExtra("correctiveActionId", row.id)
        startActivity(intent)
    }

    private fun showToast(message: String?, duration: Int) {
        Toast.makeText(this, message, duration).show()
    }
}
