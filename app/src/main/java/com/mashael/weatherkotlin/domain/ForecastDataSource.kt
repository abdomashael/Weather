package com.mashael.weatherkotlin.domain

import com.mashael.weatherkotlin.domain.ForecastList


interface ForecastDataSource{
    fun requestForecastByCoordinates(coordinates: String,date:Long): ForecastList?
}