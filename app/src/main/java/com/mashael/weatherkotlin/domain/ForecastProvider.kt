package com.mashael.weatherkotlin.domain

import com.mashael.weatherkotlin.data.db.ForecastDb
import com.mashael.weatherkotlin.data.server.ForecastServer
import com.mashael.weatherkotlin.extensions.firstResult

class ForecastProvider(private val sources: List<ForecastDataSource> =
                           SOURCES
) {
    companion object {
        const val DAY_IN_MILLIS=1000*60*60*24
        val SOURCES by lazy {  listOf(ForecastDb(), ForecastServer())}

    }

    fun requestByCoordinates(coordinates: String,days:Int):ForecastList= requestToSources {
        val res = it.requestForecastByCoordinates(coordinates, todayTimeSpan())
        if (res != null && res.size >= days) res else null
    }

    fun requestForecast(id: Long): Forecast = requestToSources { it.requestDayForecast(id) }


    private fun <T : Any> requestToSources(f: (ForecastDataSource) -> T?): T = sources.firstResult { f(it) }


    private fun todayTimeSpan()=System.currentTimeMillis()/ DAY_IN_MILLIS * DAY_IN_MILLIS
}

