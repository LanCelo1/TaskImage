package com.example.taskapp.data.repository

import com.example.taskapp.data.model.ImageData
import com.example.taskapp.data.model.ResultData

interface AppRepository {
    fun getImages(page : Int, limit : Int): ResultData<List<ImageData>>
}