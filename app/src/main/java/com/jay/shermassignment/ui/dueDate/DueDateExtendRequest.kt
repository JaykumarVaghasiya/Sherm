package com.jay.shermassignment.ui.dueDate

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.jay.shermassignment.R
import com.jay.shermassignment.generic.showGenericDateDialog
import com.jay.shermassignment.generic.showToast
import com.jay.shermassignment.utils.SessionManager
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DueDateExtendRequest : AppCompatActivity() {

    private lateinit var preferredDateText:MaterialTextView
    private lateinit var preferredDateButton: MaterialButton
    private lateinit var commentText:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_due_date_extend_request)
        supportActionBar?.setTitle(R.string.due_date_extended_request)

        initializeViewsId()

        preferredDateButton.setOnClickListener {
            showGenericDateDialog(R.string.selectedDate.toString(), System.currentTimeMillis()) { selectedDate ->
                val formattedDate =
                    SimpleDateFormat("dd-MM-yyyy", Locale.US).format(Date(selectedDate))
                preferredDateText.text = formattedDate
            }
        }


    }
    private fun initializeViewsId() {
        preferredDateText=findViewById(R.id.tvPreferredDate)
        preferredDateButton=findViewById(R.id.btnPreferredDate)
        commentText=findViewById(R.id.etComments)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.save_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.save) {
            sendDueDateRequest()
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun sendDueDateRequest(){
        val id = intent.getIntExtra("correctiveActionId",0)

        val authToken = SessionManager(this).fetchAuthToken()

        lifecycleScope.launch {
            val dueDateExtendResponse=try{
                DueDateInstance.api.getDueDateRequest(id,authToken!!)
            }catch (e: HttpException){
                showToast( e.message)
                return@launch
            }catch (e: IOException){
                showToast( e.message)
                return@launch
            }

            if (dueDateExtendResponse.isSuccessful && dueDateExtendResponse.body() != null){
                showToast( getString(R.string.due_date_extended_request))
            }
        }
    }
}