package com.example.taskapp.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.taskapp.MainActivity
import com.example.taskapp.data.local.MySharedPreference
import com.example.taskapp.utils.LocaleHelper
import com.example.taskapp.utils.NetworkMonitor
import dagger.hilt.android.HiltAndroidApp
import java.util.*
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {
    @Inject
    lateinit var sharedPreference: MySharedPreference
    override fun onCreate() {
        super.onCreate()
        if (sharedPreference.themeNight) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        NetworkMonitor(this).startNetworkCallback()
    }

}