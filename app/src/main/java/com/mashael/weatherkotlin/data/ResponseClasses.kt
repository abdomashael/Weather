package com.mashael.weatherkotlin.data

data class ForecastResult(val city: City, val list: List<Forecast>)

data class CurrentForecastResult(val name: String,val weather:List<Weather>,val main: Main,val wind: Wind)

data class City(
    val id: Long, val name: String, val coord: Coordinates,
    val country: String, val population: Int
)

data class Coordinates(val lon: Float, val lat: Float)
data class Forecast(
    val dt: Long, val temp: Temp, val pressure: Float,
    val humidity: Int, val weather: List<Weather>, val speed: Float,
    val deg: Int, val clouds: Int, val rain: Float
)

data class Temp(
    val day: Float, val min: Float, val max: Float,
    val night: Float, val eve: Float, val morn: Float
)

data class Weather(
    val id: Int, val main: String, val description: String
    , val icon: String
)

data class Main(val temp:Float,val pressure: Float,val humidity: Int,
                val temp_min: Float,val temp_max: Float,
                val sea_level:Float,val grnd_level:Float)

data class Wind(val speed: Float,val deg: Float)