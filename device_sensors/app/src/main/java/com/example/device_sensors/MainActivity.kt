package com.example.device_sensors

import android.content.Context
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var sensorManager: SensorManager
    private lateinit var sensorCategorySpinner: Spinner
    private lateinit var textViewSensors: TextView

    private val environmentSensors = listOf("Temperature Sensor", "Humidity Sensor", "Light Sensor")
    private val positionSensors = listOf("Accelerometer", "Magnetic Field Sensor", "Gyroscope")
    private val humanStateSensors = listOf("Heart Rate Sensor", "Step Counter", "Proximity Sensor")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorCategorySpinner = findViewById(R.id.list_sensor)
        textViewSensors = findViewById(R.id.textViewSensors)

        setupSpinner()
    }

    private fun setupSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.type_sensors, android.R.layout.simple_spinner_item
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        sensorCategorySpinner.adapter = adapter

        sensorCategorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View?, position: Int, id: Long) {
                updateSensorList(position)
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                textViewSensors.text = ""
            }
        }
    }

    private fun updateSensorList(categoryPosition: Int) {
        val sensorList = StringBuilder()
        val selectedSensors = when (categoryPosition) {
            0 -> environmentSensors // Датчики окружающей среды
            1 -> positionSensors    // Датчики положения устройства
            2 -> humanStateSensors   // Датчики состояния человека
            else -> listOf()         // Пустой список
        }

        selectedSensors.forEach { sensor ->
            sensorList.append("$sensor\n")
        }

        textViewSensors.text = sensorList.toString()
    }
}

