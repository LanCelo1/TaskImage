package com.example.taskapp.data.model

sealed class ResultData<out T>{
    data class Success<T>(val data : T) : ResultData<T>()
    data class Error( val message : String) : ResultData<Nothing>()
}
