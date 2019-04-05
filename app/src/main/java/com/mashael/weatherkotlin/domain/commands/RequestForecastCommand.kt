package com.mashael.weatherkotlin.domain.commands

import com.mashael.weatherkotlin.data.server.ForecastRequest
import com.mashael.weatherkotlin.domain.Command
import com.mashael.weatherkotlin.domain.ForecastDataMapper
import com.mashael.weatherkotlin.domain.ForecastList

class RequestForecastCommand(val cityCoordinates: String) :
    Command<ForecastList> {
    override fun execute(): ForecastList {
        val forcastRequest = ForecastRequest(cityCoordinates)
        return ForecastDataMapper().convertFromDataModel(forcastRequest.execute())
    }
}