package com.mashael.weatherkotlin.data.server

import com.mashael.weatherkotlin.domain.ForecastList
import java.util.*
import java.util.concurrent.TimeUnit
import com.mashael.weatherkotlin.domain.Forecast as ModelForecast

class ServerDataMapper {
    fun convertToDomain(coordinates: String,forecast: ForecastResult)= with(forecast){
        ForecastList(coordinates,city.name,city.country,convertForecastListToDomain(list))
    }

    private fun convertForecastListToDomain(list: List<Forecast>): List<ModelForecast> {
        return list.mapIndexed{i,forecast ->
            val  dt=Calendar.getInstance().timeInMillis+TimeUnit.DAYS.toMillis(i.toLong())
            convertForecastItemToDomain(forecast.copy(dt=dt))
        }

    }

    private fun convertForecastItemToDomain(forecast: Forecast)= with(forecast) {
        ModelForecast(-1,dt,weather[0].description,temp.max.toInt(),temp.min.toInt(),generateIconUrl(weather[0].icon))

    }

    private fun generateIconUrl(iconCode: String)="http://openweathermap.org/img/w/$iconCode.png"
}