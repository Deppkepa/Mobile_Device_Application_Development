package com.example.workmanager

import android.os.Bundle
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

import retrofit2.await

class WeatherWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    private val apiKey = "24ee95b2cd78b919fa583c775cb4142d"

    override fun doWork(): Result {
        val cityIDs = inputData.getIntArray("cities")
        val results = StringBuilder()

        if (cityIDs != null) {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val weatherApi = retrofit.create(WeatherApi::class.java)
            for (cityId in cityIDs) {
                val response = weatherApi.getWeather(cityId, apiKey, "ru").execute()
                if (response.isSuccessful) {
                    val weatherResponse = response.body()
                    results.append("ID Города: $cityId, Город: ${weatherResponse?.name}, Температура: ${weatherResponse?.main?.temp}, Описание: ${weatherResponse?.weather?.get(0)?.description} \n\n")
                    Log.d("mytag", "ID Города: $cityId, Город: ${weatherResponse?.name}, Температура: ${weatherResponse?.main?.temp}, Описание: ${weatherResponse?.weather?.get(0)?.description}")
                } else {
                    results.append("Не смогли получить погоду для города с ID: $cityId \n\n")
                }
            }
        }
        Log.d("mytag", "успех")
        val outputData = workDataOf("result" to results.toString())
        return Result.success(outputData)
    }
}
