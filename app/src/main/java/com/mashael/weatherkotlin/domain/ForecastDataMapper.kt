package com.mashael.weatherkotlin.domain

import com.mashael.weatherkotlin.data.Forecast
import com.mashael.weatherkotlin.data.ForecastResult
import java.text.DateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import com.mashael.weatherkotlin.domain.Forecast as ModelForecast

class ForecastDataMapper {
    fun convertFromDataModel(forecast: ForecastResult): ForecastList {
        return com.mashael.weatherkotlin.domain.ForecastList(
            forecast.city.name,
            forecast.city.country,
            convertForecastListToDomain(forecast.list)
        )
    }
    private fun convertForecastListToDomain(list: List<Forecast>):List<ModelForecast>{
        return list.mapIndexed { i, forcast->
            val dt = Calendar.getInstance().timeInMillis+TimeUnit.DAYS.toMillis(i.toLong())
        convertForecastItemToDomain(forcast.copy(dt=dt))
        }
    }

    private fun convertForecastItemToDomain(forcast:Forecast):ModelForecast{
        return ModelForecast(convertDate(forcast.dt),forcast.weather[0].description,
            forcast.temp.max.toInt(),forcast.temp.min.toInt(),generateIconUrl(forcast.weather[0].icon))
    }
    private fun generateIconUrl(iconCode:String):String = "http://openweathermap.org/img/w/$iconCode.png"

    private fun convertDate(date: Long):String{
        val df=DateFormat.getDateInstance(DateFormat.SHORT,Locale.getDefault())
        return df.format(date)
    }
}