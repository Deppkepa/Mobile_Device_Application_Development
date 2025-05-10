package com.example.menu


data class Coord(
    var lon: Float,
    var lat: Float
)

data class weather_(
    var id: Int,
    var main: String,
    var description: String,
    var icon: String
)

data class Main (
    var temp: Float,
    var feels_like: Float,
    var temp_min: Float,
    var temp_max: Float,
    var pressure: Float,
    var humidity: Float,
    var sea_level: Float,
    var grnd_level: Float
)

data class Wind(
    var speed: Float,
    var deg: Float
)

data class Clouds(
    var all: Int
)

data class Sys_(
    var type: Int,
    var id: Int,
    var country: String,
    var sunrise: Int,
    var sunset: Int
)

data class Weather(
    var coord: Coord,
    var weather: List<weather_>,
    var base: String,
    var main: Main,
    var visibility: Float,
    var wind: Wind,
    var clouds: Clouds,
    var dt:Float,
    var sys: Sys_,
    var timezone: Int,
    var id: Int,
    var name: String,
    var cod: Float
)