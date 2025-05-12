package com.example.waiter

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var textViewState: TextView
    private lateinit var buttonStop: Button

    private var minutesPassed = 0
    private var timeReceiverRegistered = false


    private val timeTickReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == Intent.ACTION_TIME_TICK) {
                minutesPassed++
                updateStatusText()
            }
        }
    }


    private val chargingReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                Intent.ACTION_POWER_CONNECTED -> {
                    Toast.makeText(
                        context,
                        getString(R.string.charging),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                Intent.ACTION_POWER_DISCONNECTED -> {
                    Toast.makeText(
                        context,
                        getString(R.string.not_charging),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun updateStatusText() {
        textViewState.text = getString(R.string.waiting_time, minutesPassed)
    }

    private fun registerReceivers() {

        registerReceiver(timeTickReceiver, IntentFilter(Intent.ACTION_TIME_TICK))
        timeReceiverRegistered = true


        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
        }
        registerReceiver(chargingReceiver, filter)


        val batteryStatus = registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        val plugged = batteryStatus?.getIntExtra("plugged", -1) ?: -1
        val isCharging = plugged == 1 || plugged == 2 || plugged == 4
        Toast.makeText(
            this,
            getString(if (isCharging) R.string.charging else R.string.not_charging),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun unregisterTimeReceiver() {
        if (timeReceiverRegistered) {
            unregisterReceiver(timeTickReceiver)
            timeReceiverRegistered = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewState = findViewById(R.id.textViewState)
        buttonStop = findViewById(R.id.buttonStop)

        updateStatusText()
        registerReceivers()

        buttonStop.setOnClickListener {
            unregisterTimeReceiver()
            Toast.makeText(
                this,
                getString(R.string.tired_toast),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            unregisterTimeReceiver()
            unregisterReceiver(chargingReceiver)
        } catch (_: Exception) {}
    }
}
