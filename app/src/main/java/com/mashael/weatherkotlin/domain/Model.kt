package com.mashael.weatherkotlin.domain

interface Command<out T> {
    fun execute(): T
}

data class ForecastList(val id: String, val city: String, val country: String, val dailyForecast: List<Forecast>) {
    val size: Int
        get() = dailyForecast.size

    operator fun get(position: Int): Forecast = dailyForecast[position]
}

data class Forecast(
    /*val id: String,*/val date: Long, val description: String,
    val high: Int,val low: Int, val iconUrl: String
)

data class CurrentForecast(
    val main: String, val description: String, val iconUrl: String,
    val temp: Int, val pressure: Int, val humidity: Int,
    val speed: Int, val deg: Float
)