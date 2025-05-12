package com.example.menu

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import java.io.File
import java.util.Date

object WeatherCacheHelper {
    private fun getCacheFile(context: Context, city: String): File {

        return File(context.filesDir, "weather_cache_${city.lowercase()}.json")
    }

    fun saveWeatherToCache(context: Context, city: String, weather: Weather) {
        val file = getCacheFile(context, city)
        file.writeText(Gson().toJson(weather))
        file.setLastModified(Date().time)
        Log.d("weather_cache", "Weather JSON saved for $city: ${file.absolutePath}")
    }

    fun loadWeatherFromCache(context: Context, city: String): Weather? {
        val file = getCacheFile(context, city)
        return if (file.exists()) {
            Log.d("weather_cache", "Reading weather JSON for $city: ${file.absolutePath}")
            try {
                Gson().fromJson(file.readText(), Weather::class.java)
            } catch (e: Exception) {
                Log.e("weather_cache", "Error parsing cache: ${e.message}")
                null
            }
        } else null
    }

    fun isCacheOutdated(context: Context, city: String, maxMinutes: Int): Boolean {
        val file = getCacheFile(context, city)
        if (!file.exists()) return true
        val now = Date().time
        val lastModified = file.lastModified()
        val minutesDiff = (now - lastModified) / 60000
        Log.d("weather_cache", "Cache age for $city: $minutesDiff min, limit: $maxMinutes min")
        return minutesDiff > maxMinutes
    }
}