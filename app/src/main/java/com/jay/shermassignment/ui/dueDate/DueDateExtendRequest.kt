package com.jay.shermassignment.ui.dueDate

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.jay.shermassignment.R
import com.jay.shermassignment.generic.showGenericDateDialog
import com.jay.shermassignment.generic.showToast
import com.jay.shermassignment.utils.SessionManager
import retrofit2.HttpException
import kotlinx.coroutines.launch
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

        initializeViewsId()

        preferredDateButton.setOnClickListener {
            showGenericDateDialog(R.string.selectedDate.toString(), System.currentTimeMillis() , { selectedDate ->
                val formattedDate = SimpleDateFormat("dd-MM-yyyy", Locale.US).format(Date(selectedDate))
                preferredDateText.text = formattedDate
            }, this)
        }
        val id = intent.getIntExtra("correctiveActionId",0)

        val authToken = SessionManager(this).fetchAuthToken()

        lifecycleScope.launch {
            val dueDateExtendResponse=try{
                DueDateInstance.api.getDueDateRequest(id,authToken!!)
            }catch (e:HttpException){
                showToast(e.message)
                return@launch
            }catch (e:IOException){
                showToast(e.message)
                return@launch
            }

            if (dueDateExtendResponse.isSuccessful && dueDateExtendResponse.body() != null){
                showToast(this@DueDateExtendRequest,"Due Date Request Created")

            }
        }

    }
    private fun initializeViewsId() {
        preferredDateText=findViewById(R.id.tvPreferredDate)
        preferredDateButton=findViewById(R.id.btnPreferredDate)
        commentText=findViewById(R.id.etComments)

    }
    private fun showToast(message: String?) {
        Toast.makeText(this@DueDateExtendRequest, message, Toast.LENGTH_SHORT).show()
    }

}