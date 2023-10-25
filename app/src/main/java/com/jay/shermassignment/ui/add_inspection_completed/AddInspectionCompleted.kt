package com.jay.shermassignment.ui.add_inspection_completed

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.jay.shermassignment.R
import com.jay.shermassignment.generic.showCustomDialog
import com.jay.shermassignment.generic.showGenericDateDialog
import com.jay.shermassignment.generic.showToast
import com.jay.shermassignment.response.addinspectioncompletted.CompletedInspectionBody
import com.jay.shermassignment.utils.SessionManager
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddInspectionCompleted : AppCompatActivity() {

    private lateinit var inspectionCompleted:MaterialTextView
    private lateinit var calendarInspectionCompleted: MaterialButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_inspection_completed)
        supportActionBar?.setTitle(R.string.add_inspections_completed)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initializeId()
        buttonClickListener()
    }

    private fun initializeId(){
        inspectionCompleted=findViewById(R.id.tvInspectionCompletedDate)
        calendarInspectionCompleted=findViewById(R.id.btnCalenderInspectionCompleted)
    }

    private fun buttonClickListener() {
        calendarInspectionCompleted.setOnClickListener {
            showGenericDateDialog(R.string.selectedDate.toString(), System.currentTimeMillis()) { selectedDate ->
                val formattedDate =
                    SimpleDateFormat("dd-MM-yyyy", Locale.US).format(Date(selectedDate))
                inspectionCompleted.text = formattedDate
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->{
                finish()
            }
            R.id.save ->{
                saveData()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveData() {
        val authToken = SessionManager(this).fetchAuthToken()
        val id = intent.getIntExtra("iId",0)
        val completedDate=inspectionCompleted.text.toString()
        val body = CompletedInspectionBody(completedDate,id)

        lifecycleScope.launch {
            val response = try {
                CompletedInstance.api.completedScheduledInspection(body,authToken!!)
            }catch (e:Exception){
                showToast(e.message)
                return@launch
            }catch (e:HttpException){
                showToast(e.message)
                return@launch
            }catch (e:IOException){
                showToast(e.message)
                return@launch
            }

            if(response.isSuccessful && response.body()!= null){
                showCustomDialog(
                    R.string.sucess,
                    R.string.complted
                )
            }
        }
    }
}