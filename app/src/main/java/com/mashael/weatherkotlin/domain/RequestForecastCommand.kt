package com.mashael.weatherkotlin.domain

import com.mashael.weatherkotlin.data.Coordinates
import com.mashael.weatherkotlin.data.ForecastRequest

class RequestForecastCommand(val cityCoordinates: String) : Command<ForecastList> {
    override fun execute(): ForecastList {
        val forcastRequest = ForecastRequest(cityCoordinates)
        return ForecastDataMapper().convertFromDataModel(forcastRequest.execute())
    }
}