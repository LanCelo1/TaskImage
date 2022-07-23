package com.example.taskapp.presentation.presenter.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskapp.data.model.ImageData
import com.example.taskapp.data.model.ResultData
import com.example.taskapp.domain.MainUseCase
import com.example.taskapp.presentation.presenter.viewmodel.MainScreenViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModelImpl @Inject constructor(
    private val useCase: MainUseCase
) : MainScreenViewModel,ViewModel() {
    var vrPageOld = -1
    var hrPageOld = -1
    override var hrImageDataListLiveData = MutableLiveData<List<ImageData>>()
    override var vrImageDataListLiveData = MutableLiveData<List<ImageData>>()
    override var getErrorLiveData = MutableLiveData<String>()


    override fun loadHrViews(page : Int) {
       /* if (hrPageOld == page) return
        if (hrPageOld == -1) hrPageOld = page*/
        useCase.getImages(page,100).onEach {resultData ->
            when(resultData){
                is ResultData.Success->{
                    resultData.data?.let {
                        hrImageDataListLiveData.postValue(it)
                    }
                }
                is ResultData.Error->{
                    getErrorLiveData.postValue("hr " + resultData.message)
                }
            }
        }.launchIn(viewModelScope)
    }

    override fun loadVrViews(page : Int) {
       /* if (vrPageOld == page) return
        if (vrPageOld == -1) vrPageOld = page*/
        useCase.getImages(page,100).onEach {resultData ->
            when(resultData){
                is ResultData.Success->{
                    resultData.data?.let {
                        vrImageDataListLiveData.postValue(it)
                    }
                }
                is ResultData.Error->{
                    getErrorLiveData.postValue("vr" + resultData.message)
                }
            }
        }.launchIn(viewModelScope)
    }
}