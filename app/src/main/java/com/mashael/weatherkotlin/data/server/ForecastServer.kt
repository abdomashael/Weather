package com.mashael.weatherkotlin.data.server

import com.mashael.weatherkotlin.domain.ForecastDataSource
import com.mashael.weatherkotlin.data.db.ForecastDb
import com.mashael.weatherkotlin.domain.ForecastList

class ForecastServer(private val dataMapper: ServerDataMapper = ServerDataMapper(),
                     private val forecastDb: ForecastDb =ForecastDb()):
    ForecastDataSource {
    override fun requestForecastByCoordinates(coordinates: String, date: Long): ForecastList? {
        val result = ForecastRequest(coordinates).execute()
        val converted = dataMapper.convertToDomain(coordinates,result)
        forecastDb.saveForecast(converted)
        return forecastDb.requestForecastByCoordinates(coordinates,date)
    }
}