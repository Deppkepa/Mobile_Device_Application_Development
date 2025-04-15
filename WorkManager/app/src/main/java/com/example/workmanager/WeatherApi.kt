package com.example.workmanager

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

data class WeatherResponse(
    val main: Main,
    val weather: List<Weather>,
    val name: String
)

data class Main(
    val temp: Double
)

data class Weather(
    val description: String
)

interface WeatherApi {
    @GET("weather")
    fun getWeather(
        @Query("id") cityName: Int,
        @Query("appid") apiKey: String,
        @Query("lang") lang: String
    ): Call<WeatherResponse>
}
