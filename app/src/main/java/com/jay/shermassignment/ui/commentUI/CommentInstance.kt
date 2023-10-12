package com.jay.shermassignment.ui.commentUI

import com.jay.shermassignment.utils.RetrofitInstance

object CommentInstance {
    val api: CommentApi by lazy {
        RetrofitInstance.api
            .create(CommentApi::class.java)
    }
}