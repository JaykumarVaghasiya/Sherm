package com.jay.shermassignment.ui.correctiveaction

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
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
import com.jay.shermassignment.ui.CAViewActivity
import com.jay.shermassignment.utils.SessionManager
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class CorrectiveAction : AppCompatActivity() {
    private lateinit var correctiveActionRecyclerView: RecyclerView
    private lateinit var correctiveActionAdapter: CorrectiveActionAdapter
    private lateinit var progressBar:ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_corrective_action)

        correctiveActionRecyclerView = findViewById(R.id.rvCorrectiveAction)
        progressBar=findViewById(R.id.pbCorrectiveAction)
        progressBar.bringToFront()
        progressBar.visibility=View.VISIBLE
        correctiveActionAdapter = CorrectiveActionAdapter(this)

        val inspectionLayoutManager = LinearLayoutManager(this)
        correctiveActionRecyclerView.layoutManager = inspectionLayoutManager
        correctiveActionRecyclerView.adapter = correctiveActionAdapter
        val authToken = SessionManager(this).fetchAuthToken()

        loadCorrectiveAction(authToken!!)
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.ispection_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.add) {
            val intent = Intent(this, CAViewActivity::class.java)
            startActivity(intent)
            overridePendingTransition(
                com.google.android.material.R.anim.abc_grow_fade_in_from_bottom,
                R.anim.slide_out_to_left
            )
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadCorrectiveAction(authToken: String) {
        lifecycleScope.launch {
            val correctiveActionResponse = try {
                CorrectiveActionInstance.api.getAllCorrectiveAction(
                    CorrectiveActionData(
                        2888, 1, "action", "asc", "Risk"
                    ), "Bearer $authToken"
                )

            } catch (e: IOException) {
                Toast.makeText(this@CorrectiveAction, e.message, Toast.LENGTH_SHORT).show()
                return@launch
            } catch (e: HttpException) {
                Toast.makeText(this@CorrectiveAction, e.message, Toast.LENGTH_SHORT).show()
                return@launch
            }

            if (correctiveActionResponse.isSuccessful && correctiveActionResponse.body() != null) {
                correctiveActionAdapter.submitInspectionList(correctiveActionResponse.body()!!.content.rows)
                progressBar.visibility = View.GONE
            } else {
                Toast.makeText(
                    this@CorrectiveAction,
                    R.string.empty_data_or_response,
                    Toast.LENGTH_LONG
                )
                    .show()
                progressBar.visibility = View.GONE
            }
        }


        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }
}