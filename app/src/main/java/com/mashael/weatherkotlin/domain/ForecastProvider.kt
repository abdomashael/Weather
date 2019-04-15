package com.mashael.weatherkotlin.domain

import com.mashael.weatherkotlin.data.db.ForecastDb
import com.mashael.weatherkotlin.data.server.ForecastServer
import com.mashael.weatherkotlin.extensions.firstResult

class ForecastProvider(private val sources: List<ForecastDataSource> =
                           SOURCES
) {
    companion object {
        const val DAY_IN_MILLIS=1000*60*60*24
        val SOURCES = listOf(ForecastDb(), ForecastServer())

    }

    fun requestByCoordinates(coordinates: String,days:Int):ForecastList
    = sources.firstResult{requestSource(it,days,coordinates)}

    private fun requestSource(source: ForecastDataSource, days: Int, coordinates: String): ForecastList? {
        val res = source.requestForecastByCoordinates(coordinates,todayTimeSpan())
        return if (res!=null && res.size >=days) res else null
    }

    private fun todayTimeSpan()=System.currentTimeMillis()/ DAY_IN_MILLIS * DAY_IN_MILLIS
}

