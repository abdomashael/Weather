package com.mashael.weatherkotlin.data.server

import com.google.gson.Gson
import java.net.URL

class ForecastRequest(private val cityCoordinates:String){

    companion object {
        private const val APP_ID="15646a06818f61f7b8d7823ca833e1ce"
        private const val URL="http://api.openweathermap.org/data/2.5/forecast/daily?"
        private const val COMPLETE_URL="$URL&APPID=$APP_ID"
        private const val METRIC="&units=metric"
    }

    fun execute(): ForecastResult {
        val forecastJsonStr = URL(COMPLETE_URL +cityCoordinates+ METRIC).readText()
        return Gson().fromJson(forecastJsonStr, ForecastResult::class.java)
    }
}