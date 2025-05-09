package com.example.alertdialog

import com.example.alertdialog.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    fun getWeather(
        @Query("q") city: String,
        @Query("appid") API_KEY: String,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "RU"
    ): Call<Weather>
}