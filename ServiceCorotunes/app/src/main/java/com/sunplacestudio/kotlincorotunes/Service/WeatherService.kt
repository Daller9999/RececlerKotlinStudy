package com.sunplacestudio.kotlincorotunes.Service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.sunplacestudio.kotlincorotunes.MainActivity
import com.sunplacestudio.kotlincorotunes.R
import kotlinx.coroutines.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class WeatherService: Service() {
    private val WEATHER_ID = "Weather Service"
    private val anyHttp = "http://api.openweathermap.org/data/2.5/weather?q=Moscow&appid=22dfebc0ca27764c84210035034c1a62&units=metric"

    @Volatile
    private var isRun = false
    private var binder = WeatherBinder()
    private var callBack: DataCallBack? = null
    private val scope = CoroutineScope(Dispatchers.Main)
    private var lastText: String = ""

    companion object {
        fun startService(context: Context) {
            val startIntent = Intent(context, WeatherService::class.java)
            ContextCompat.startForegroundService(context, startIntent)
        }

        fun stopService(context: Context) {
            val stopIntent = Intent(context, WeatherService::class.java)
            context.stopService(stopIntent)
        }
    }

    inner class WeatherBinder : Binder() {
        fun getService(): WeatherService = this@WeatherService
    }

    fun getLastText(): String {
        return lastText
    }

    fun setCallBack(callback: DataCallBack) {
        this.callBack = callback
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = buildNotification("")
        run()
        startForeground(1, notification)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        isRun = false
    }

    override fun onBind(intent: Intent): IBinder? {
        return binder
    }

    private fun run() {
        if (isRun) return

        scope.launch {
            isRun = true
            while (this@WeatherService.isRun) {
                madeRequest()
                delay(15000)
            }
        }
    }

    private fun madeRequest() {
        val client = OkHttpClient()
        val request = Request.Builder().url(anyHttp).get().build()
        try {
            client.newCall(request).enqueue(httpCallBack)
        } catch (ex: Exception) {
            Log.e("mesUri", ex.toString())
        }
    }

    private val httpCallBack: Callback = object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("mesUri", e.toString())
        }

        override fun onResponse(call: Call, response: Response) {
            val text = response.body()?.string()
            val json = JSONObject(text)
            val main = json.getJSONObject("main")
            val temp = main.getDouble("temp")

            lastText = "В Москве сейчас: $temp °С; время: ${getTime()}"
            madeNotification(lastText)

            callBack?.onDataUpdate(lastText)
        }
    }

    private fun getTime(): String {
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val currentDate = sdf.format(Date())
        return currentDate
    }


    private fun madeNotification(text: String) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, buildNotification(text))
    }

    private fun buildNotification(text: String): Notification {
        createNotificationChannel()

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        val notification = NotificationCompat.Builder(this, WEATHER_ID)
            .setContentTitle("Weather Service")
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_load_data)
            .setContentIntent(pendingIntent)
            .build()
        return notification
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(WEATHER_ID, "Weather Service Channel", NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }

    interface DataCallBack {
        fun onDataUpdate(weather: String)
    }
}