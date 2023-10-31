package com.jay.shermassignment.ui.corrective_action

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.jay.shermassignment.R
import com.jay.shermassignment.generic.showConfirmationDialog
import com.jay.shermassignment.response.correctiveaction.CorrectiveActionData
import com.jay.shermassignment.response.correctiveaction.Row
import com.jay.shermassignment.ui.add_corrective_action.AddCorrectiveAction
import com.jay.shermassignment.ui.corrective_action_details.CAViewActivity
import com.jay.shermassignment.ui.corrective_evaluation.CorrectiveEvaluation
import com.jay.shermassignment.utils.SessionManager
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class CorrectiveAction : AppCompatActivity(), CorrectiveActionAdapter.OnCorrectiveActionItemClick,
    CorrectiveActionAdapter.OnCorrectiveEvaluationClick {

    private lateinit var correctiveActionRecyclerView: RecyclerView
    private lateinit var correctiveActionAdapter: CorrectiveActionAdapter
    private lateinit var progressBar: LinearLayout
    private lateinit var emptyListMessage: MaterialTextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_corrective_action)
        supportActionBar?.title = getString(R.string.corrective_action)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupViews()
        setupAdapter()
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        loadCorrectiveAction()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.ispection_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> openAddCorrectiveActionView()
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setupViews() {
        correctiveActionRecyclerView = findViewById(R.id.rvCorrectiveAction)
        progressBar = findViewById(R.id.overLay)
        emptyListMessage = findViewById(R.id.emptyListMessage)
        progressBar.bringToFront()
        progressBar.visibility = View.VISIBLE
    }

    private fun setupAdapter() {
        correctiveActionAdapter = CorrectiveActionAdapter(this, this, this)
        val inspectionLayoutManager = LinearLayoutManager(this)
        correctiveActionRecyclerView.layoutManager = inspectionLayoutManager
        correctiveActionRecyclerView.adapter = correctiveActionAdapter

        correctiveActionAdapter.registerAdapterDataObserver(object :
            RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                checkEmpty()
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                checkEmpty()
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                checkEmpty()
            }

            fun checkEmpty() {
                emptyListMessage.visibility =
                    (if (correctiveActionAdapter.itemCount == 0) View.VISIBLE else View.GONE)
            }
        })
    }

    private fun setupListeners() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }

    private fun loadCorrectiveAction() {
        val inspectionId = intent.getIntExtra("ids", 1)
        val authToken=SessionManager(this).fetchAuthToken()

        lifecycleScope.launch {
            val correctiveActionResponse = try {
                CorrectiveActionInstance.api.getAllCorrectiveAction(
                    CorrectiveActionData(inspectionId, 1, "id", "desc", "Inspection"),
                    "Bearer $authToken"
                )
            } catch (e: Exception) {
                showConfirmationDialog(getString(R.string.sherm),e.message)
                return@launch
            } catch (e: IOException) {
                showConfirmationDialog(getString(R.string.sherm),e.message)
                return@launch
            } catch (e: HttpException) {
                showConfirmationDialog(getString(R.string.sherm),e.message)
                return@launch
            }
            if (correctiveActionResponse.isSuccessful && correctiveActionResponse.body() != null) {
                correctiveActionAdapter.submitInspectionList(correctiveActionResponse.body()!!.content.rows)
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun openAddCorrectiveActionView() {
        val iId = intent.getIntExtra("ids", 0)
        val intent = Intent(this, AddCorrectiveAction::class.java)
        intent.putExtra("iId",iId)
        startActivity(intent)

    }

    override fun onItemClick(row: Row) {
        val id = row.id
        val iId = intent.getIntExtra("ids", 0)
        val intent = Intent(this, CAViewActivity::class.java)
        intent.putExtra("iId",iId)
        intent.putExtra("correctiveActionId", id)
        startActivity(intent)
    }

    override fun onCorrectiveEvaluationClick(row: Row) {
        val intent = Intent(this, CorrectiveEvaluation::class.java)
        val iId=intent.getIntExtra("ids",0)
        intent.putExtra("iId",iId)
        intent.putExtra("correctiveActionId", row.id)
        startActivity(intent)
    }


}
