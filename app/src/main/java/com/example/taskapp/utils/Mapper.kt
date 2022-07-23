package com.example.taskapp.utils

import com.example.taskapp.data.model.ImageData
import com.example.taskapp.data.remote.response.ImageItem

object Mapper {
        fun ImageItem.toModel() = ImageData(
            id = id?.toInt() ?: 0,
            author = author ?: "",
            imageUrl = download_url ?: "",
        )
}