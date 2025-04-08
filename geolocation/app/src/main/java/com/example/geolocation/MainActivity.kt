package com.example.geolocation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ComponentActivity
import androidx.core.content.ContextCompat

@SuppressLint("RestrictedApi")
class MainActivity : ComponentActivity(), LocationListener {

    private val LOCATION_PERM_CODE = 2
    private lateinit var locationManager: LocationManager
    private lateinit var statusTextView: TextView
    private lateinit var providersTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusTextView = findViewById(R.id.status)
        providersTextView = findViewById(R.id.providers)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Запрашиваем разрешения
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERM_CODE)
        } else {
            startLocationUpdates()
        }

        updateProviders()
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
            updateProviders()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERM_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates()
            } else {
                statusTextView.text = "Permission denied."
            }
        }
    }

    override fun onLocationChanged(loc: Location) {
        val lat = loc.latitude
        val lng = loc.longitude
        displayCoord(lat, lng)
        Log.d("my", "lat $lat long $lng")
        statusTextView.text = "Online"
    }

    override fun onProviderDisabled(provider: String) {
        statusTextView.text = "Offline"
    }

    override fun onProviderEnabled(provider: String) {
        statusTextView.text = "Online"
        updateProviders()
    }

    private fun updateProviders() {
        val providers = locationManager.allProviders.joinToString(", ")
        providersTextView.text = "Available Providers: $providers"
    }

    private fun displayCoord(latitude: Double, longitude: Double) {
        findViewById<TextView>(R.id.lat).text = String.format("%.5f", latitude)
        findViewById<TextView>(R.id.lng).text = String.format("%.5f", longitude)
    }
}