package com.example.menu

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.menu.databinding.FragmentMovieBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieFragment : Fragment() {

    private val viewModel: WeatherViewModel by activityViewModels()
    private val API_KEY = "24ee95b2cd78b919fa583c775cb4142d"
    private val cityList = arrayOf("Irkutsk", "Moscow", "London", "Tokyo", "Paris")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding: FragmentMovieBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_movie, container, false
        )
        binding.ctx = requireContext()
        binding.lifecycleOwner = viewLifecycleOwner
        binding.weather = null

        val cityAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, cityList)
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.citySpinner.apply {
            adapter = cityAdapter
            onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    val selectedCity = cityList[position]
                    viewModel.setCity(selectedCity)
                }
                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        }

        viewModel.city.observe(viewLifecycleOwner) { selectedCity ->
            fetchWeather(selectedCity, binding)
        }

        viewModel.weather.observe(viewLifecycleOwner) { weather ->
            binding.weather = weather
        }

        return binding.root
    }

    private fun fetchWeather(city: String, binding: FragmentMovieBinding) {
        RetrofitClient.instance.getWeather(city, API_KEY, lang = resources.getString(R.string.lang))
            .enqueue(object: Callback<Weather> {
                override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                    if (response.isSuccessful) {
                        val weatherResponse = response.body()
                        viewModel.setWeather(weatherResponse)
                    } else {
                        Log.e("Weather", "Request failed with code: ${response.code()}")
                        viewModel.setWeather(null)
                    }
                }
                override fun onFailure(call: Call<Weather>, t: Throwable) {
                    Log.e("Weather", "Error: ${t.message}")
                    viewModel.setWeather(null)
                }
            })
    }
}