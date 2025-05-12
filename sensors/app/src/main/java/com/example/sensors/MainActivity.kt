package com.example.sensors

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.sensors.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null
    private var rotationSensor: Sensor? = null
    private var accelerometerSensor: Sensor? = null

    private var selectedSensor = Sensor.TYPE_LIGHT
    private var dataSensor: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.sensText = ""

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        // По умолчанию - свет (l)
        binding.radioGroup.check(R.id.l)
        selectedSensor = Sensor.TYPE_LIGHT
        setSensText(initial = true)

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            selectedSensor = when (checkedId) {
                R.id.l -> Sensor.TYPE_LIGHT
                R.id.r -> Sensor.TYPE_ROTATION_VECTOR
                R.id.a -> Sensor.TYPE_ACCELEROMETER
                else -> Sensor.TYPE_LIGHT
            }
            setSensText(initial = true)
            unregisterAllListeners()
            registerSelectedListener()
        }
    }

    override fun onResume() {
        super.onResume()
        registerSelectedListener()
    }

    override fun onPause() {
        super.onPause()
        unregisterAllListeners()
    }

    private fun registerSelectedListener() {
        when (selectedSensor) {
            Sensor.TYPE_LIGHT -> {
                if (lightSensor == null) {
                    showSensorAbsent(R.string.sensorAbsentL)
                } else {
                    sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_UI)
                }
            }
            Sensor.TYPE_ROTATION_VECTOR -> {
                if (rotationSensor == null) {
                    showSensorAbsent(R.string.sensorAbsentR)
                } else {
                    sensorManager.registerListener(this, rotationSensor, SensorManager.SENSOR_DELAY_UI)
                }
            }
            Sensor.TYPE_ACCELEROMETER -> {
                if (accelerometerSensor == null) {
                    showSensorAbsent(R.string.sensorAbsentA)
                } else {
                    sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_UI)
                }
            }
        }
    }

    private fun unregisterAllListeners() {
        sensorManager.unregisterListener(this)
    }

    private fun showSensorAbsent(messageId: Int) {
        Toast.makeText(this, getString(messageId), Toast.LENGTH_LONG).show()
        dataSensor = ""
        binding.sensText = ""
    }

    @SuppressLint("DefaultLocale")
    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            when (it.sensor.type) {
                Sensor.TYPE_LIGHT -> {
                    dataSensor = getString(R.string.light) + ": " + it.values[0].toString()
                }
                Sensor.TYPE_ROTATION_VECTOR -> {
                    // Обычно rotation vector содержит 3 или 5 значений, но для задания - 3 проекции
                    val text = String.format(
                        "%s:\nX: %.2f\nY: %.2f\nZ: %.2f",
                        getString(R.string.rotor),
                        it.values.getOrNull(0) ?: 0f,
                        it.values.getOrNull(1) ?: 0f,
                        it.values.getOrNull(2) ?: 0f
                    )
                    dataSensor = text
                }
                Sensor.TYPE_ACCELEROMETER -> {
                    val text = String.format(
                        "%s:\nX: %.2f\nY: %.2f\nZ: %.2f",
                        getString(R.string.accelerometer),
                        it.values.getOrNull(0) ?: 0f,
                        it.values.getOrNull(1) ?: 0f,
                        it.values.getOrNull(2) ?: 0f
                    )
                    dataSensor = text
                }
            }
            binding.sensText = dataSensor
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    private fun setSensText(initial: Boolean = false) {
        when (selectedSensor) {
            Sensor.TYPE_LIGHT -> {
                binding.sensText = getString(R.string.light)
                if (initial && lightSensor == null) showSensorAbsent(R.string.sensorAbsentL)
            }
            Sensor.TYPE_ROTATION_VECTOR -> {
                binding.sensText = getString(R.string.rotor)
                if (initial && rotationSensor == null) showSensorAbsent(R.string.sensorAbsentR)
            }
            Sensor.TYPE_ACCELEROMETER -> {
                binding.sensText = getString(R.string.accelerometer)
                if (initial && accelerometerSensor == null) showSensorAbsent(R.string.sensorAbsentA)
            }
        }
    }
}