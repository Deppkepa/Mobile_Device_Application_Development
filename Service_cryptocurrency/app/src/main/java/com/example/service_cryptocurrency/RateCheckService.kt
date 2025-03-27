package com.example.service_cryptocurrency

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.example.service.Notify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.math.BigDecimal


class RateCheckService : Service() {
    val handler = Handler(Looper.getMainLooper())
    var rateCheckAttempt = 0
    lateinit var startRate: BigDecimal
    lateinit var targetRate: BigDecimal
    val rateCheckInteractor = RateCheckInteractor()
    lateinit var rate: BigDecimal
    private lateinit var notify: Notify
    private lateinit var rateCheckRunnable: Runnable

    override fun onCreate() {
        super.onCreate()
        notify = Notify(this)
    }

    private fun requestAndCheckRate() {
        GlobalScope.launch(Dispatchers.Main){
            val current_rate=BigDecimal(rateCheckInteractor.requestRate())
            if (rate<current_rate && current_rate.subtract(rate)>targetRate){
                notify.sendNotification("Rate went up", "Current rate: $current_rate ^^^")
            } else if(rate>current_rate && rate.subtract(current_rate)>targetRate){
                notify.sendNotification("Rate went down", "Current rate: $current_rate vvv")
            }
            rate=current_rate
        }
    }

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startRate = BigDecimal(intent?.getStringExtra(ARG_START_RATE))
        targetRate = BigDecimal(intent?.getStringExtra(ARG_TARGET_RATE))
        rate = startRate

        Log.d(TAG, "onStartCommand startRate = $startRate targetRate = $targetRate")
        rateCheckRunnable = object : Runnable {
            override fun run() {
                requestAndCheckRate()
                handler.postDelayed(this, 10000)
            }

        }
        handler.post(rateCheckRunnable)

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(rateCheckRunnable)
    }


    companion object {
        const val TAG = "RateCheckService"
        const val RATE_CHECK_INTERVAL = 5000L
        const val RATE_CHECK_ATTEMPTS_MAX = 100

        const val ARG_START_RATE = "ARG_START_RATE"
        const val ARG_TARGET_RATE = "ARG_TARGET_RATE"

        fun startService(context: Context, startRate: String, targetRate: String) {
            context.startService(Intent(context, RateCheckService::class.java).apply {
                putExtra(ARG_START_RATE, startRate)
                putExtra(ARG_TARGET_RATE, targetRate)
            })
        }

        fun stopService(context: Context) {
            context.stopService(Intent(context, RateCheckService::class.java))
        }
    }



}