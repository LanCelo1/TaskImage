package com.example.taskapp.presentation.ui.screen

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.taskapp.R
import com.example.taskapp.data.local.MySharedPreference
import com.example.taskapp.databinding.ScreenSettingBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class SettingScreen : Fragment(R.layout.screen_setting) {
    @Inject
    lateinit var sharedPreference: MySharedPreference
    val binding: ScreenSettingBinding by viewBinding(ScreenSettingBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            switchLayout.isChecked = sharedPreference.themeNight
            switchLayout.setOnClickListener {
                if (switchLayout.isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                sharedPreference.themeNight = binding.switchLayout.isChecked
            }
            radio.check(
                positionToId(sharedPreference.language)
            )
        }
        binding.save.setOnClickListener {
            if (sharedPreference.language != idToPosition(binding.radio.checkedRadioButtonId)) {
                val position = idToPosition(binding.radio.checkedRadioButtonId)
                val myLocale =
                    Locale(if (position == 2) "ru" else if (position == 1) "uz" else "en")
                val configuration = requireActivity().resources.configuration
                configuration.setLocale(myLocale)
                requireActivity().resources.updateConfiguration(
                    configuration,
                    resources.displayMetrics
                )
                sharedPreference.language = position
                requireActivity().recreate()
            }
        }
    }


    private fun positionToId(position: Int): Int {
        return when (position) {
            0 -> R.id.english
            1 -> R.id.uzbek
            2 -> R.id.russian
            else -> R.id.english
        }
    }

    private fun idToPosition(id: Int): Int {
        return when (id) {
            R.id.english -> {
                0
            }
            R.id.uzbek -> {
                1
            }
            R.id.russian -> {
                2
            }
            else -> {
                0
            }
        }
    }
}