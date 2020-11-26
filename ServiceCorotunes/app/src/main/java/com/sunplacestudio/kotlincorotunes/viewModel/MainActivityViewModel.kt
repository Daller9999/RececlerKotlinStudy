package com.sunplacestudio.kotlincorotunes.viewModel

import android.content.ComponentName
import android.content.Context
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.databinding.ObservableField
import com.sunplacestudio.kotlincorotunes.Service.WeatherService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val context: Context
) {

    val weatherString: ObservableField<String> = ObservableField()

    private var weatherService: WeatherService? = null
    private var isBind: Boolean = false

    private val serviceConnection: ServiceConnection = object: ServiceConnection {

        override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
            val binder = service as WeatherService.WeatherBinder
            weatherService = binder.getService()
            weatherService?.setCallBack(callBack)
            isBind = true
            weatherString.set(weatherService?.getLastText())
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isBind = false
        }
    }

    private val callBack: WeatherService.DataCallBack = object : WeatherService.DataCallBack {
        override fun onDataUpdate(weather: String) {
            weatherString.set(weather)
        }
    }

    init {
        bindService()
    }

    private fun bindService() {
        if (isBind) return
        context.bindService(Intent(context, WeatherService::class.java), serviceConnection, BIND_AUTO_CREATE)
        isBind = true
    }

    fun startService() {
        WeatherService.startService(context)
        bindService()
    }

    fun stopService() {
        WeatherService.stopService(context)
        dismiss()
    }

    fun dismiss() {
        if (!isBind) return
        context.unbindService(serviceConnection)
        isBind = false
    }
}