package com.example.taskapp.data.remote

import com.example.taskapp.data.remote.response.ImageItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApi {
    @GET("v2/list")
    fun getList(
        @Query("page") page : Int,
        @Query("limit") limit : Int
        ) : Call<List<ImageItem>>
}