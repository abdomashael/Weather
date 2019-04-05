package com.mashael.weatherkotlin.domain.commands

import com.mashael.weatherkotlin.data.server.CurrentForecastRequest
import com.mashael.weatherkotlin.domain.Command
import com.mashael.weatherkotlin.domain.CurrentForecast
import com.mashael.weatherkotlin.domain.ForecastDataMapper

class RequestCurrentForecastCommand(val cityCoordinates: String) :
    Command<CurrentForecast> {

    override fun execute(): CurrentForecast {
        val currentForecastRequest = CurrentForecastRequest(cityCoordinates)
        return ForecastDataMapper().convertCurrentFromDataModel(currentForecastRequest.execute())

    }
}