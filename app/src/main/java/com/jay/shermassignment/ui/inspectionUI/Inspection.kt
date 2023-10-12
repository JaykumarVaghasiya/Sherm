package com.jay.shermassignment.ui.inspectionUI

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jay.shermassignment.R
import com.jay.shermassignment.model.inspection.InspectionRef
import com.jay.shermassignment.model.inspection.Row
import com.jay.shermassignment.ui.inspectionDetailsUI.AddInspectionActivity
import com.jay.shermassignment.ui.inspectionDetailsUI.ShowInspectionDetailsActivity
import com.jay.shermassignment.utils.SessionManager
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class Inspection : AppCompatActivity(), InspectionAdapter.OnInspectionListener,
    InspectionAdapter.OnDeleteListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var inspectionAdapter: InspectionAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inspection)

        recyclerView = findViewById(R.id.rvInspection)
        inspectionAdapter = InspectionAdapter(this, this, this)

        val inspectionLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = inspectionLayoutManager
        recyclerView.adapter = inspectionAdapter



        val authToken = SessionManager(this).fetchAuthToken()

        lifecycleScope.launch {
            val inspectionResponse = try {
                InspectionDetailsInstance.api.getInspectionDetails(
                    InspectionRef(
                        "", "", 1, null, "completedDate", arrayListOf(), "asc", "false"
                    ), "Bearer $authToken"
                )
            } catch (e: IOException) {
                Toast.makeText(this@Inspection, e.message, Toast.LENGTH_SHORT).show()
                return@launch
            } catch (e: HttpException) {
                Toast.makeText(this@Inspection, e.message, Toast.LENGTH_SHORT).show()
                return@launch
            }

            if (inspectionResponse.isSuccessful && inspectionResponse.body() != null) {
                inspectionAdapter.submitInspectionList(inspectionResponse.body()!!.content.rows)
            } else {
                Toast.makeText(this@Inspection, R.string.empty_data_or_response, Toast.LENGTH_LONG)
                    .show()
            }
        }

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.ispection_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.add) {
          val intent=Intent(this, AddInspectionActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onDeleteClicked(row: Row) {
        val authToken = SessionManager(this).fetchAuthToken()
        lifecycleScope.launch {
            val inspectionResponse = try {
                val queryParameters = mapOf(
                    "inspectionId" to row.inspectionId
                )
                InspectionDetailsInstance.api.deleteInspectionItem(
                    queryParameters,
                    "Bearer $authToken"
                )

            } catch (e: IOException) {
                Toast.makeText(this@Inspection, e.message, Toast.LENGTH_SHORT).show()
                return@launch

            } catch (e: HttpException) {
                Toast.makeText(this@Inspection, e.message, Toast.LENGTH_SHORT).show()
                return@launch
            }
            if (inspectionResponse.isSuccessful && inspectionResponse.body() != null) {
                inspectionAdapter.deleteInspection(row)
            } else {
                Toast.makeText(this@Inspection, "Can't deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onInspectionClicked(row: Row) {
        val intent=Intent(this, ShowInspectionDetailsActivity::class.java)
        intent.putExtra("id",row.inspectionId)
        startActivity(intent)
    }
}