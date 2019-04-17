package com.mashael.weatherkotlin.domain.commands

import com.mashael.weatherkotlin.domain.Command
import com.mashael.weatherkotlin.domain.Forecast
import com.mashael.weatherkotlin.domain.ForecastProvider

class RequestDayForecastCommand(
    val id: Long,
    private val forecastProvider: ForecastProvider = ForecastProvider()
) :
    Command<Forecast> {

    override fun execute() = forecastProvider.requestForecast(id)
}