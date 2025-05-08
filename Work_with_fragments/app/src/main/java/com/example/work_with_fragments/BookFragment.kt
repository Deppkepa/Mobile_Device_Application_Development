package com.example.work_with_fragments


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

class BookFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_book, container, false)

        val API_KEY="d6843ab8ee963f5d372296dfff62aed7"
        val city="Irkutsk"
        RetrofitClient.instance.getWeather(city, API_KEY).enqueue(object : Callback<Weather> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                if (response.isSuccessful) {
                    val weatherResponse = response.body()
                    weatherResponse?.let {
                        view.findViewById<TextView>(R.id.location).text= "Город: ${it.name}"
                        view.findViewById<TextView>(R.id.temperature).text= "Температура: ${it.main.temp} ℃"
                        view.findViewById<TextView>(R.id.wind).text= "Направление ветра: ${it.wind.speed} м/с"
                        view.findViewById<TextView>(R.id.humidity).text= "Влажность: ${it.main.humidity} кг/м³"
                        view.findViewById<TextView>(R.id.weather).text= "Погода: ${it.weather[0].description}   "

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