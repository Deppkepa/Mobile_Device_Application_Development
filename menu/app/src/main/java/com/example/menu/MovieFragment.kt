package com.example.menu

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie, container, false)

        val API_KEY="24ee95b2cd78b919fa583c775cb4142d"
        val city="Irkutsk"
        RetrofitClient.instance.getWeather(city, API_KEY, lang=resources.getString(R.string.lang)).enqueue(object : Callback<Weather> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                if (response.isSuccessful) {
                    val weatherResponse = response.body()
                    weatherResponse?.let {
                        view.findViewById<TextView>(R.id.location).text= "${resources.getString(R.string.city)}: ${it.name}"
                        view.findViewById<TextView>(R.id.temperature).text= "${resources.getString(R.string.temp)}: ${it.main.temp} ℃"
                        view.findViewById<TextView>(R.id.wind).text= "${resources.getString(R.string.wind)}: ${it.wind.speed} м/с"
                        view.findViewById<TextView>(R.id.humidity).text= "${resources.getString(R.string.humidity)}: ${it.main.humidity} кг/м³"
                        view.findViewById<TextView>(R.id.weather).text= "${resources.getString(R.string.weather)}: ${it.weather[0].description}"
                        view.findViewById<TextView>(R.id.feels_like).text= "${resources.getString(R.string.feels)}: ${it.main.feels_like} ℃"
                        view.findViewById<TextView>(R.id.pressure).text= "${resources.getString(R.string.pressure)}: ${it.main.pressure} Па"
                    }
                } else {
                    Log.e("Weather", "Request failed with code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Weather>, t: Throwable) {
                Log.e("Weather", "Error: ${t.message}")
            }
        })

        return view
    }
}