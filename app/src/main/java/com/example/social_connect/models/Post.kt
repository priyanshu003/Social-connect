package com.example.social_connect.models

data class Post(
        val text: String = "",
        val createdBy: User = User(),
        val createdAt: Long = 0L,
        val image2: String = "",
        val likedBy: ArrayList<String> = ArrayList()

)
