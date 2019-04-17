package com.mashael.weatherkotlin.data.db

import com.mashael.weatherkotlin.domain.Forecast
import com.mashael.weatherkotlin.domain.ForecastList

class DbDataMapper {
    fun convertFromDomain(forecast: ForecastList)= with(forecast) {
        val daily = dailyForecast.map { convertDayFromDomain(id,it) }
        CityForecast(id,city,country,daily)
    }

    private fun convertDayFromDomain(cityId: String, forecast: Forecast)= with(forecast) {
        DayForecast(date,description,high,low,iconUrl,cityId)
    }

    fun convertToDomain(forecast: CityForecast)= with(forecast) {
        val daily = dailyForecast.map { convertDayToDomain(it) }
        ForecastList(_id,city,country,daily)
    }

    fun convertDayToDomain(dayForecast: DayForecast)= with(dayForecast) {
        Forecast(_id,date,description,high,low,iconUrl)
    }
}