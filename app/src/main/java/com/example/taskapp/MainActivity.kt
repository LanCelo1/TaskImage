package com.example.taskapp

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.taskapp.data.local.MySharedPreference
import com.example.taskapp.databinding.ActivityMainBinding
import com.example.taskapp.presentation.ui.screen.MainScreen
import com.example.taskapp.presentation.ui.screen.SettingScreen
import com.example.taskapp.utils.LocaleHelper
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main)