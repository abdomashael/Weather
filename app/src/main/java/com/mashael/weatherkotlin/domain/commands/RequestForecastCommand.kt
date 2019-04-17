package com.mashael.weatherkotlin.domain.commands

import com.mashael.weatherkotlin.domain.Command
import com.mashael.weatherkotlin.domain.ForecastList
import com.mashael.weatherkotlin.domain.ForecastProvider

class RequestForecastCommand(val cityCoordinates: String,
                             val forecastProcider: ForecastProvider = ForecastProvider()
) :
    Command<ForecastList> {

    companion object{
        val DAYS=7
    }
    override fun execute(): ForecastList {
        return forecastProcider.requestByCoordinates(cityCoordinates, DAYS)
    }
}