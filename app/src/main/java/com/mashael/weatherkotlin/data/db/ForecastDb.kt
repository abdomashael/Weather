package com.mashael.weatherkotlin.data.db

import android.database.sqlite.SQLiteDatabase
import com.mashael.weatherkotlin.domain.Forecast
import com.mashael.weatherkotlin.domain.ForecastDataSource
import com.mashael.weatherkotlin.domain.ForecastList
import com.mashael.weatherkotlin.extensions.byId
import com.mashael.weatherkotlin.extensions.parseList
import com.mashael.weatherkotlin.extensions.parseOpt
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class ForecastDb(val forecastDbHelper: ForecastDbHelper=
                     ForecastDbHelper.instance, val dataMapper:DbDataMapper=DbDataMapper()):
    ForecastDataSource {
    override fun requestDayForecast(id: Long)= forecastDbHelper.use {
        val forecast = select(ForecastDbHelper.DayForecastTable.NAME).byId(id).
            parseOpt { DayForecast(HashMap(it)) }

        if (forecast != null) dataMapper.convertDayToDomain(forecast) else null
    }
    override fun requestForecastByCoordinates(coordinates:String,date :Long)
            =forecastDbHelper.use {
        val dailyRequest = "${ForecastDbHelper.DayForecastTable.CITY_ID}=?"+
                "AND ${ForecastDbHelper.DayForecastTable.DATE}>=?"

        val dailyForecast = select(ForecastDbHelper.DayForecastTable.NAME)
            .whereSimple(dailyRequest,coordinates,date.toString())
            .parseList{DayForecast(HashMap(it))}

        val city= select(ForecastDbHelper.CityForecastTable.NAME)
            .whereSimple("${ForecastDbHelper.CityForecastTable.ID}=?",coordinates)
            .parseOpt{CityForecast(HashMap(it),dailyForecast)}

        if (city != null) dataMapper.convertToDomain(city) else null
    }



    fun saveForecast(forecast: ForecastList)=forecastDbHelper.use {
        clear(ForecastDbHelper.CityForecastTable.NAME)
        clear(ForecastDbHelper.DayForecastTable.NAME)

        with(dataMapper.convertFromDomain(forecast)){
            insert(ForecastDbHelper.CityForecastTable.NAME, *map.toVarargArray())
            dailyForecast.forEach{
                insert(ForecastDbHelper.DayForecastTable.NAME,*it.map.toVarargArray())
            }
        }
    }

    fun SQLiteDatabase.clear(tableName:String){
        execSQL("delete from $tableName")
    }


}

private fun <K, V> MutableMap<K, V>.toVarargArray():
        Array<out Pair<K,V>> = map( {Pair(it.key,it.value)}).toTypedArray()


