package com.mashael.weatherkotlin.data.server

import com.google.gson.Gson
import java.net.URL

class CurrentForecastRequest(private val cityCoordinates:String){

    companion object {
        private const val APP_ID="15646a06818f61f7b8d7823ca833e1ce"
        private const val CURRENT_URL="https://api.openweathermap.org/data/2.5/weather?"
        private const val CURRENT_COMPLETE_URL="$CURRENT_URL&APPID=$APP_ID"
        private const val METRIC="&units=metric"
    }

    fun execute(): CurrentForecastResult {
        val forecastJsonStr = URL(CURRENT_COMPLETE_URL +cityCoordinates+ METRIC).readText()
        return Gson().fromJson(forecastJsonStr, CurrentForecastResult::class.java)
    }
}