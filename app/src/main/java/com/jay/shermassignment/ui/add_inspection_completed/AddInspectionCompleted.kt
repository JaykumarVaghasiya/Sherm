package com.jay.shermassignment.ui.add_inspection_completed

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.jay.shermassignment.R
import com.jay.shermassignment.generic.showConfirmationDialog
import com.jay.shermassignment.generic.showGenericDateDialog
import com.jay.shermassignment.dataModel.addinspectioncompletted.CompletedInspectionBody
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
    private var inspectionId: Int = 0
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
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(selectedDate))
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
                setResult(RESULT_CANCELED)
            }
            R.id.save ->{
                saveData()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveData() {
        val authToken = SessionManager(this).fetchAuthToken()
        inspectionId= intent.getIntExtra("ids",0)
        val completedDate=inspectionCompleted.text.toString()
        val body = CompletedInspectionBody(completedDate,inspectionId)

        lifecycleScope.launch {
            val response = try {
                CompletedInstance.api.completedScheduledInspection(body,authToken!!)
            }catch (e:Exception){
                showConfirmationDialog(getString(R.string.sherm),e.message)
                return@launch
            }catch (e:HttpException){
                showConfirmationDialog(getString(R.string.sherm),e.message)
                return@launch
            }catch (e:IOException){
                showConfirmationDialog(getString(R.string.sherm),e.message)
                return@launch
            }

            if(response.isSuccessful && response.body()!= null){
                showConfirmationDialog(
                    getString(R.string.sucess),
                    getString(R.string.complted)
                ){
                    setResult(RESULT_OK)
                    finish()
                }
            }
        }

    }
}