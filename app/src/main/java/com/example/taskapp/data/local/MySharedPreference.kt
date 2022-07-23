package com.example.taskapp.data.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import com.example.taskapp.utils.SharedPreference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MySharedPreference @Inject constructor(@ApplicationContext context : Context) : SharedPreference(context){
    var language : Int by IntPreference(0)
    var themeNight : Boolean by BooleanPreference(false)
}