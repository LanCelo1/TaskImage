package com.example.taskapp.domain

import com.example.taskapp.data.model.ImageData
import com.example.taskapp.data.model.ResultData
import kotlinx.coroutines.flow.Flow

interface MainUseCase {
    fun getImages(page: Int, limit: Int): Flow<ResultData<List<ImageData>>>
}