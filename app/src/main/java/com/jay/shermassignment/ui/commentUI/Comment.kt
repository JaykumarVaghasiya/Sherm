package com.jay.shermassignment.ui.commentUI

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jay.shermassignment.R
import com.jay.shermassignment.generic.showToast
import com.jay.shermassignment.response.comments.CommentBody
import com.jay.shermassignment.utils.SessionManager
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class Comment : AppCompatActivity() {

    private lateinit var comment: EditText
    private var inspectionId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)
        supportActionBar?.setTitle(R.string.inspection_comments)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        comment = findViewById(R.id.etInspectionComment)
        inspectionId = intent.getIntExtra("ids", 0)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.save_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> saveComment()
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveComment() {
        val commentText = comment.text.toString()
        val authToken = SessionManager(this).fetchAuthToken()
        val commentBody = CommentBody(commentText, inspectionId)

        lifecycleScope.launch {
            val commentResponse =
            try {
                CommentInstance.api.addCommentsForInspection(commentBody, authToken!!)
            } catch (e: Exception) {
                showToast("Error: ${e.message}")
                return@launch
            } catch (e: HttpException) {
                showToast("Error: ${e.message}")
                return@launch
            } catch (e: IOException) {
                showToast("Error: ${e.message}")
                return@launch
            }
            if (commentResponse.isSuccessful && commentResponse.body() != null) {
                showToast(getString(R.string.successfully_comment))
                finish()
            }
        }
    }
}