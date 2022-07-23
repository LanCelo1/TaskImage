package com.example.taskapp.presentation.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.taskapp.data.model.ImageData

interface MainScreenViewModel{
    fun loadHrViews(page : Int)
    fun loadVrViews(page : Int)

    val hrImageDataListLiveData: LiveData<List<ImageData>>
    val getErrorLiveData: LiveData<String>
    val vrImageDataListLiveData: LiveData<List<ImageData>>
}