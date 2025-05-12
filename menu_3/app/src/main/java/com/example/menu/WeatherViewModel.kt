package com.example.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WeatherViewModel : ViewModel() {
    private var _weather = MutableLiveData<Weather?>()
    var weather: MutableLiveData<Weather?> = _weather

    private var _city = MutableLiveData("Irkutsk")
    var city: MutableLiveData<String> = _city

    fun setCity(city: String) {
        _city.value = city
    }

    fun setWeather(weather: Weather?) {
        _weather.value = weather
    }
}