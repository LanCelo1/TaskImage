package com.example.taskapp.data.remote.response

data class ImageItem(
    val id : String,
    val author : String,
    val width : Int,
    val height : Int,
    val url : String,
    val download_url : String
)
