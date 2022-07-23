package com.example.taskapp.data.repository.impl

import android.media.Image
import com.example.taskapp.data.model.ImageData
import com.example.taskapp.data.model.ResultData
import com.example.taskapp.data.remote.NetworkApi
import com.example.taskapp.data.repository.AppRepository
import com.example.taskapp.utils.Mapper
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val api: NetworkApi
) : AppRepository {
    override fun getImages(page: Int, limit: Int): ResultData<List<ImageData>> {
        try {
            val response = api.getList(page, limit).execute()
            if (response.isSuccessful) {
                response.body()?.let {
                    try {
                        val models = it.map { Mapper.run { it?.let { it.toModel() } ?:ImageData(0,"","") } }
                        return ResultData.Success(models)
                    }catch (e : Exception){
                        ResultData.Error(e.message.toString())
                    }
                }
            }
            return ResultData.Error(message = response.errorBody()?.let { it.string().toString() }
                ?: "")
        }catch (e : Exception){
            ResultData.Error(e.message.toString())
        }
        return ResultData.Error("unknown error")
    }
}