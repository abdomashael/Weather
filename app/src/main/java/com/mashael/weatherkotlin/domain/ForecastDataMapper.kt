package com.mashael.weatherkotlin.domain

import com.mashael.weatherkotlin.data.server.CurrentForecastResult
import com.mashael.weatherkotlin.data.server.Forecast
import com.mashael.weatherkotlin.data.server.ForecastResult
import java.text.DateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import com.mashael.weatherkotlin.domain.Forecast as ModelForecast

class ForecastDataMapper {
   /* fun convertFromDataModel(coordiantes: String, forecast: ForecastResult) = with(forecast) {
        ForecastList(
            coordiantes,
            forecast.city.name,
            forecast.city.country,
            convertForecastListToDomain(forecast.list)
        )
    }*/

    fun convertCurrentFromDataModel(currentForecastResult: CurrentForecastResult): CurrentForecast {
        return CurrentForecast(
            currentForecastResult.weather[0].main,
            currentForecastResult.weather[0].description,
            generateIconUrl(currentForecastResult.weather[0].icon),
            currentForecastResult.main.temp.toInt(),
            currentForecastResult.main.pressure.toInt(),
            currentForecastResult.main.humidity,
            currentForecastResult.wind.speed.toInt(),
            currentForecastResult.wind.deg
        )
    }

 /*   private fun convertForecastListToDomain(list: List<Forecast>): List<ModelForecast> {
        return list.mapIndexed { i, forecast ->
            val dt = Calendar.getInstance().timeInMillis + TimeUnit.DAYS.toMillis(i.toLong())
            convertForecastItemToDomain(forecast.copy(dt = dt))
        }
    }

    private fun convertForecastItemToDomain(forcast: Forecast) = with(forcast) {
        ModelForecast(dt, weather[0].description, temp.max.toInt(), temp.min.toInt(),
            generateIconUrl(weather[0].icon))
    }


    private fun convertDate(date: Long): String {
        val df = DateFormat.getDateInstance(DateFormat.MONTH_FIELD, Locale.getDefault())
        return df.format(date)
    }*/
 private fun generateIconUrl(iconCode: String): String = "http://openweathermap.org/img/w/$iconCode.png"

}