package com.example.taskapp.domain.impl

import com.example.taskapp.data.model.ImageData
import com.example.taskapp.data.model.ResultData
import com.example.taskapp.data.repository.AppRepository
import com.example.taskapp.domain.MainUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

class MainUseCaseImpl @Inject constructor(
    private val repository: AppRepository
) : MainUseCase {
    override fun getImages(page : Int, limit : Int) = flow<ResultData<List<ImageData>>>{
        emit(repository.getImages(page, limit))
    }.flowOn(Dispatchers.IO)
}