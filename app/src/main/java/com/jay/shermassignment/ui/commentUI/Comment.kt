package com.jay.shermassignment.ui.commentUI

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.jay.shermassignment.R
import com.jay.shermassignment.dataModel.comments.CommentBody
import com.jay.shermassignment.databinding.ActivityCommentBinding
import com.jay.shermassignment.di.viewmodels.comment.CommentViewModel
import com.jay.shermassignment.generic.showConfirmationDialog

class Comment : AppCompatActivity() {

  private lateinit var commentViewModel: CommentViewModel
  private lateinit var binding:ActivityCommentBinding
    private var inspectionId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setTitle(R.string.inspection_comments)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        commentViewModel=ViewModelProvider(this)[CommentViewModel::class.java]
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
        val commentText = binding.etInspectionComment.text.toString()
        if(commentText.isEmpty()){
            showConfirmationDialog(getString(R.string.error),getString(R.string.comment_required))
        }

        val commentBody = CommentBody(commentText, inspectionId)

        commentViewModel.addCorrectiveAction(commentBody)
        commentViewModel._addCommentLiveData.observe(this){comment->
            if(comment?.isSuccess ==true){
                showConfirmationDialog(getString(R.string.sucess),getString(R.string.successfully_comment)){
                    finish()
                }
            }
        }
        commentViewModel._errorMessageLiveData.observe(this){error->
            if(error != null){
                showConfirmationDialog(getString(R.string.error),error)
            }
        }

    }
}