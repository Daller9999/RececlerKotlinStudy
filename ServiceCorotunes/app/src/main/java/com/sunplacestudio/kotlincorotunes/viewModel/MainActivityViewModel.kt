package com.sunplacestudio.kotlincorotunes.viewModel

import android.content.Context
import androidx.databinding.ObservableField
import com.sunplacestudio.kotlincorotunes.Service.WeatherService

class MainActivityViewModel(
    private val context: Context
) {

    val weather: ObservableField<String> = ObservableField()

    fun startService() {
        WeatherService.startService(context)
    }

    fun stopService() {
        WeatherService.stopService(context)
    }

}