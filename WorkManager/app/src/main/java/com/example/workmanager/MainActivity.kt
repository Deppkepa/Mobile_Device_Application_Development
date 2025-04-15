package com.example.workmanager


import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.workDataOf


class MainActivity : AppCompatActivity() {
    private lateinit var weatherTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        weatherTextView = findViewById(R.id.weatherTextView)
        val citiesData = workDataOf("cities" to intArrayOf(2643743, 2023469, 1850144, 300))
        val workRequest = OneTimeWorkRequest.Builder(WeatherWorker::class.java)
            .setInputData(citiesData)
            .build()

        WorkManager.getInstance(this).enqueue(workRequest)
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequest.id).observe(this) { workInfo ->
            if (workInfo != null && workInfo.state.isFinished) {
                val result = workInfo.outputData.getString("result") ?: "No data"
                weatherTextView.text = result
            }
        }

    }
}