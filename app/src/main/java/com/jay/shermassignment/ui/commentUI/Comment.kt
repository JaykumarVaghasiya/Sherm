package com.jay.shermassignment.ui.commentUI

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jay.shermassignment.R
import com.jay.shermassignment.model.comments.CommentBody
import com.jay.shermassignment.utils.SessionManager
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class Comment : AppCompatActivity() {

    private lateinit var comment: EditText
    private val inspectionId = intent.getIntExtra("id", 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)
        comment = findViewById(R.id.etInspectionComment)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.save_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.save) {
            val commentText = comment.text.toString()
            val authToken = SessionManager(this).fetchAuthToken()


            val commentBody = CommentBody(commentText, inspectionId)

            lifecycleScope.launch {
                val commentResponse = try {
                    CommentInstance.api.addCommentsForInspection(commentBody, authToken!!)
                } catch (e: HttpException) {
                    showToast(e.message)
                    return@launch
                } catch (e: IOException) {
                    showToast(e.message)
                    return@launch
                }

                if (commentResponse.isSuccessful && commentResponse.body() != null) {

                }
            }
            finish()
            overridePendingTransition(
                com.google.android.material.R.anim.abc_grow_fade_in_from_bottom,
                R.anim.slide_out_to_left
            )
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showToast(message: String?) {
        Toast.makeText(this@Comment, message, Toast.LENGTH_SHORT).show()
    }
}